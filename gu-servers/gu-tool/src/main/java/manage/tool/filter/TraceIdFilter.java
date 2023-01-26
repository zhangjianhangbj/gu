package manage.tool.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import manage.tool.utils.TraceIdUtils;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/12/08 11:31
 **/
@WebFilter(urlPatterns = "/*", filterName = "traceIdFilter")
@Order(1)
@Component
public class TraceIdFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String traceId = req.getHeader(TraceIdUtils.TRACE_ID);
        TraceIdUtils.setTraceId(traceId);
        filterChain.doFilter(request, response);
    }
}
