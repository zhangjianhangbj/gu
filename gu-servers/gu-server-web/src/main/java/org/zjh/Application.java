package org.zjh;

import java.time.Duration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = {"org.zjh.*"})
@SpringBootApplication(scanBasePackages = { "org.zjh.*" })
@EnableFeignClients(basePackages = "org.zjh.*")
@MapperScan({"org.zjh.*.*.mapper"})
@EnableAsync
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(20000L))
            .setReadTimeout(Duration.ofMillis(20000L)).build();
    }
}
