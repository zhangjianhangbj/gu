package manage.tool.bean;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/08 09:52
 **/
@Configuration
public class BeanFactory {
    private DozerBeanMapper instance;

    public BeanFactory() {
    }

    public synchronized DozerBeanMapper getInstance() {
        if (this.instance == null) {
            this.instance = new DozerBeanMapper();
        }

        return this.instance;
    }

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return this.getInstance();
    }
}
