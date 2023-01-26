package manage.tool.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author wangzhibo
 * @Date 2020/4/28 下午4:11
 */
@Configuration
public class CommonThreadPoolConfig {

    @Bean
    public CommonThreadPool commonThreadPool(){
        CommonThreadPool commonThreadPool = new CommonThreadPool();
        commonThreadPool.setCoreSize(20);
        commonThreadPool.setMaxSize(100);
        commonThreadPool.setAliveSeconds(60);
        commonThreadPool.setCapacity(100);
        return  commonThreadPool;
    }
}
