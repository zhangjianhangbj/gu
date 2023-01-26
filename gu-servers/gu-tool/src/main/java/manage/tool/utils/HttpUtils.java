package manage.tool.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/09/15 15:13
 **/
@Slf4j
@Component
public class HttpUtils {

    private static Integer CON_TIME_OUT=10*60*1000;
    private static Integer REQ_TIME_OUT=5*60*1000;
    /**
     * @param url            请求路径
     * @param body           请求数据
     * @return result        请求响应信息
     */
    public static String httpDoPost(String url,  String body) {
        log.info("http->post请求，url：{}",url);
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
        //设置超时
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CON_TIME_OUT).setConnectionRequestTimeout(REQ_TIME_OUT).setSocketTimeout(CON_TIME_OUT).build();
        httpPost.setConfig(requestConfig);

        HttpResponse httpResponse = null;
        try {
            StringEntity stringEntity = new StringEntity(body, "UTF-8");

            httpPost.setEntity(stringEntity);
            long startTime = System.currentTimeMillis();
            log.info("线程[{}]http请求开始时间：{}",Thread.currentThread().getName(),startTime);
            httpResponse = httpclient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            log.info("线程[{}]http请求结束时间：{},时间差{}秒",Thread.currentThread().getName(),endTime,(endTime-startTime)/1000);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            log.info("http请求结束");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            log.error("http状态编码=" + httpResponse.getStatusLine().getStatusCode() + "");
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if(httpResponse!= null){
                    httpResponse.getEntity().getContent().close();
                }
                httpPost.releaseConnection();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }
}
