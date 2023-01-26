//package manage.tool.utils.redis;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * @author chenpeng
// * @version 1.0;
// * @file RedisCache.java
// * @date 2018年11月20日
// */
//@Component
//public class RedisCache {
//
//    private final static Logger logger = LoggerFactory.getLogger(RedisCache.class);
//
//    @Autowired
//    StringRedisTemplate redisTemplate;
//
//
//    /**
//     * 单条获取
//     *  @param key
//     *  @return
//     */
//    public String get(String key) {
//        String result = redisTemplate.opsForValue().get(key);
//        return result;
//    }
//
//    /**
//     * 数值单条获取
//     *  @param key
//     *  @return
//     */
//    public Integer getInteger(String key) {
//        Integer result = Integer.getInteger(redisTemplate.opsForValue().get(key));
//        return result;
//    }
//
//
//    /**
//     * 单条添加
//     *  @param key
//     *  @param value
//     */
//    public void set(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    /**
//     * 设置带过期时间的key
//     *  @param key
//     *  @param value
//     *  @param timeout 单位秒
//     */
//    public void set(String key, String value, long timeout) {
//        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
//    }
//
//
//    //单条删除
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//
//
//
//
//    /**
//     * 设置key增加减少，并设置过期时间S
//     *
//     * @param key
//     * @param v       增减数值
//     * @param timeout 过期时间-秒
//     * @return
//     * @author zhanggaopeng
//     * @date 2019年6月11日
//     * @version 1.0
//     */
//    public long incExpire(String key, long v, long timeout) {
//        long inc = redisTemplate.boundValueOps(key).increment(v);
//        if (inc == 1) {
//            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
//        }
//        return inc;
//    }
//
//    /**
//     * 获取年月日+一天内不重复的自增数字
//     *
//     * @param keyPre key前缀
//     * @return
//     */
//    public Long getIncrementNum(String keyPre){
//        //获取年月日
//        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        //对于当前key,过期时间24小时,每次查询自增1
//        Long increment = incExpire(keyPre+date,1,3600*24);
//        //返回生成的编码
//        return Long.valueOf(date+increment);
//    }
//
//    /**
//     * 获取年月日+一天内不重复的7位随机数字
//     *
//     * @return
//     */
//    public String getCode(){
//        //保存不重复数字的key前缀
//        String keyPre = RedisKeyConstant.COMMON_DATA_CODE_KEY;
//        //获取年月日
//        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        //获取七位随机数
//        String random = pad(7,(int) (10000000 * Math.random()));
//        //key前缀+年月日
//        String key = keyPre+date;
//        long add = 0;
//        int loop = 0;
//        while (add==0){
//            //如果循环大于10,随机数改为10位
//            if (loop>10){
//                random = random + (int)(Math.random()*10);
//            }
//            //以 key前缀+年月日 为redis的key,保存七位随机数
//            add = addSet(keyPre+date, random);
//            loop++;
//        }
//        //查询key是否过期
//        Long timeOutSeconds = redisTemplate.getExpire(key, TimeUnit.SECONDS);
//        if (timeOutSeconds<0){
//            redisTemplate.expire(key,24 , TimeUnit.HOURS);
//        }
//        //返回生成的编码
//        return date + random;
//    }
//
//    /**
//     * 添加set值
//     *
//     * @param key key
//     * @param value value
//     * @return
//     */
//    public Long addSet(String key,String value){
//        return redisTemplate.opsForSet().add(key, value);
//    }
//
//    /**
//     * 获取set值
//     *
//     * @param key
//     * @return
//     */
//    public Set<String> getSet(String key){
//        return redisTemplate.opsForSet().members(key);
//    }
//
//    /**
//     * 累加
//     *
//     * @param key
//     * @param num
//     * @return
//     */
//    public Integer addNum(String key,Integer num){
//        Long increment = redisTemplate.opsForValue().increment(key, num);
//        return increment.intValue();
//    }
//
//    /**
//     * 自动补齐位数
//     * @param length 补齐后的位数
//     * @param num 待补齐的数值
//     * @return
//     */
//    public static String pad(int length,long num){
//        return String.format("%0".concat(String.valueOf(length)).concat("d"), num);
//    }
//
//
//}
