//package manage.tool.utils.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.config.ConfigureRedisAction;
//
//import java.time.Duration;
//
///**
// * @author: zhangyanfei
// * @description:
// * @create: 2022/09/06 18:45
// **/
//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {
//
//
//    @Bean
//    public CacheManager cacheManager(@Autowired RedisConnectionFactory connectionFactory) {
//        return RedisCacheManager
//                .builder(connectionFactory)
//                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
//                .transactionAware()
//                .build();
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        Jackson2JsonRedisSerializer valueRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        //设置Redis的value为json格式,并存储对象信息的序列化类型
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//
////        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
//        valueRedisSerializer.setObjectMapper(objectMapper);
//        RedisSerializer keyRedisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(keyRedisSerializer);
//        template.setValueSerializer(valueRedisSerializer);
//        template.setHashKeySerializer(keyRedisSerializer);
//        template.setHashValueSerializer(valueRedisSerializer);
//        template.setConnectionFactory(factory);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean
//    public static ConfigureRedisAction configureRedisAction() {
//        return ConfigureRedisAction.NO_OP;
//    }
//}
