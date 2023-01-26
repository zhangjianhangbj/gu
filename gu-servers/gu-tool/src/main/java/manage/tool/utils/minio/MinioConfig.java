package manage.tool.utils.minio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/09/07 14:25
 **/
@Configuration
public class MinioConfig {

    @ApiModelProperty("服务器ip")
    @Value("${minio.endpoint}")
    private String endpoint ;

    @ApiModelProperty("账号")
    @Value("${minio.accessKey}")
    private String accessKey ;

    @ApiModelProperty("密码")
    @Value("${minio.secretKey}")
    private String secretKey ;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
