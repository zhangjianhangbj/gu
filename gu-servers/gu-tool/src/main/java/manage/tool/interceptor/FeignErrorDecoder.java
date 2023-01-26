package manage.tool.interceptor;

import com.alibaba.fastjson.JSONObject;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import cn.hutool.json.JSONException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import manage.tool.exception.BaseException;

/**
 * @author: zhangyanfei
 * @description: feign的异常传递
 * @create: 2022/12/23 11:29
 **/
@Configuration
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(final String methodKey, final Response response) {
        try {
            String message = Util.toString(response.body().asReader());
            log.info("Feign exception:{}",message);
            try {
                JSONObject jsonObject = JSONObject.parseObject(message);
                log.info("Feign exception handle json code:{},json message:{}",jsonObject.getString("code"),jsonObject.getString("message"));
                // 包装成自己自定义的异常
                return new BaseException(jsonObject.getString("code"), jsonObject.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException ignored) {
        }
        return decode(methodKey, response);
    }
}

