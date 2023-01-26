//package manage.tool.utils.redis;
//
//
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KeyExpiredEventMessageListener extends KeyExpirationEventMessageListener {
//
//
//    public KeyExpiredEventMessageListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//        // 配置springboot默认Config为空，即不让应用去修改redis的默认配置，因为Redis服务出于安全会禁用CONFIG命令给远程用户使用
//        setKeyspaceNotificationsConfigParameter("");
//    }
//}