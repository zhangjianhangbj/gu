package manage.tool.filter;

import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * @author zhangyanfei
 */
@Slf4j
@WebFilter
@Component
public class LogParamFilter extends OncePerRequestFilter implements Ordered {
    private static Logger log = LoggerFactory.getLogger(LogParamFilter.class);
    public static final String SPLIT_STRING_M = "=";
    public static final String SPLIT_STRING_DOT = ", ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 输出请求log
        log.info("--------------------------------Request Start--------------------------------");
        log.info("Request Time: {} {}", LocalDate.now(), LocalTime.now());
        log.info("Request IP: {}", getIpAddr(request));
        log.info("Request URL: {} {}", request.getMethod(),request.getRequestURI());
        Map<String, String> map = Maps.newHashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement().toLowerCase();
            map.put(header, request.getHeader(header));
        }
        String urlParams = getRequestParams(request);
        log.info("Request Headers: {}", map);
        log.info("Request URL Params: {}", urlParams);

        StopWatch stopWatch = new StopWatch();
        // 通知开始时计时
        stopWatch.start();
        RequestWrapper wrapperRequest = new RequestWrapper(request);
        log.info("Request Body: {}", wrapperRequest.getBody());
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(wrapperRequest, wrapperResponse);

        String responseBodyStr = getResponseBody(wrapperResponse);

        stopWatch.stop();

        log.info("Request done in {}ms", stopWatch.getTotalTimeMillis());
        log.info("Response Body: {}", responseBodyStr);
        wrapperResponse.copyBodyToResponse();
        log.info("--------------------------------Response Success--------------------------------");

    }

    /**
     * 打印返回参数
     *
     * @param response
     */
    private String getResponseBody(ContentCachingResponseWrapper response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return "";
    }


    public String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (null != ip && ip.contains(",")) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }


    /**
     * 获取请求地址上的参数
     *
     * @param request
     * @return
     */
    public static String getRequestParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> enu = request.getParameterNames();
        //获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            sb.append(name + SPLIT_STRING_M).append(request.getParameter(name));
            if (enu.hasMoreElements()) {
                sb.append(SPLIT_STRING_DOT);
            }
        }
        return sb.toString();
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body = new byte[0];

    public RequestWrapper(HttpServletRequest request) throws IOException {

        super(request);
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            //防止未初始化body,我们手动初始化body ,内部会将body内容初始化到InputStream里
            request.getParameterMap();
            //然后在读取InputStream
            inputStream = request.getInputStream();
            //保存一份InputStream，将其转换为字节数组
            body = StreamUtils.copyToByteArray(inputStream);
        } catch (IOException ex) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }


}

class ResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private HttpServletResponse response;
    private PrintWriter pwrite;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new MyServletOutputStream(bytes); // 将数据写到 byte 中
    }

    /**
     * 重写父类的 getWriter() 方法，将响应数据缓存在 PrintWriter 中
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        try {
            pwrite = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return pwrite;
    }

    /**
     * 获取缓存在 PrintWriter 中的响应数据
     *
     * @return
     */
    public byte[] getBytes() {
        if (null != pwrite) {
            pwrite.close();
            return bytes.toByteArray();
        }

        if (null != bytes) {
            try {
                bytes.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes.toByteArray();
    }

    class MyServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream ostream;

        public MyServletOutputStream(ByteArrayOutputStream ostream) {
            this.ostream = ostream;
        }

        @Override
        public void write(int b) throws IOException {
            ostream.write(b); // 将数据写到 stream　中
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }
}
