package com.example.Kafka.Manager;

import com.example.Kafka.ApplicationConstants.TimeType;
import com.example.Kafka.Config.SpringContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisManager {
    private Environment env;
    private RedisTemplate<String, Object> template;
    private StringRedisTemplate strTemplate;
    private RedisAtomicLong uniqueNbr;
    private boolean stringExists = false;
    private boolean objectExists = false;
    private HashOperations<String, Object, Object> hashOperations;
    @SuppressWarnings("unchecked")
    private RedisManager(){
        template = (RedisTemplate<String, Object>)SpringContext.getBean("redisTemplate");
        strTemplate = (StringRedisTemplate)SpringContext.getBean("strRedisTemplate");
        env = SpringContext.getBean(Environment.class);

        hashOperations = template.opsForHash();
    }

    static class SingletonHelper {
        static RedisManager INSTANCE = new RedisManager();
    }
    public static RedisManager getInstance() {
        RedisManager redisM = SingletonHelper.INSTANCE;
        if(redisM == null){
            synchronized (RedisManager.class) {
                redisM = new RedisManager();
                SingletonHelper.INSTANCE = redisM;
            }
        }
        return redisM;
    }
    private void setTimeout(RedisTemplate<String, Object> template, String key, Integer timeout) {
        if(timeout != null){
            String time_unit = env.getProperty("redis.time_unit");
            if(time_unit.equals(TimeType.SECONDS.getValue()))
                template.expire(key, timeout.longValue(), TimeUnit.SECONDS);
            if(time_unit.equals(TimeType.MINUTES.getValue()))
                template.expire(key, timeout.longValue(), TimeUnit.MINUTES);
            else if(time_unit.equals(TimeType.HOURS.getValue()))
                template.expire(key, timeout.longValue(), TimeUnit.HOURS);
            else if(time_unit.equals(TimeType.DAYS.getValue()))
                template.expire(key, timeout.longValue(), TimeUnit.DAYS);
            else
                template.expire(key, timeout.longValue(), TimeUnit.MILLISECONDS);
        }
    }
    public void setHashAll(String key, Map<Object, Object> map, Boolean expire) {
        hashOperations.putAll(key, map);
        if(expire.booleanValue()){
            Integer timeout = Integer.parseInt(env.getProperty("redis.expire"));
            setTimeout(template, key, timeout);
        }
    }
    public Object getObjectInHash(String key, Object hashKey) {
        Object obj = hashOperations.get(key, hashKey);

        return obj;
    }
    public Map<Object, Object> getHash(String key) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map = hashOperations.entries(key);

        return map;
    }
    public void setObjectInHash(String key, Object hashKey, Object value, Boolean expire) {
        hashOperations.put(key, hashKey, value);
        if(expire.booleanValue()){
            Integer timeout = Integer.parseInt(env.getProperty("redis.expire"));
            setTimeout(template, key, timeout);
        }
    }
    public void clearObjectInHash(String key, Object hashKey){
        Object obj = this.getObjectInHash(key, hashKey);

        if(obj != null) {
            hashOperations.delete(key, hashKey);
        }
    }
}
