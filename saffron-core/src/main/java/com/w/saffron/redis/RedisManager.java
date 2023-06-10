package com.w.saffron.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author w
 * @since 2023/3/23
 */
@UtilityClass
public class RedisManager {

    public RedisTemplate<String,String> getRedisTemplate(){
        return SpringUtil.getBean("redisTemplate");
    }

    public void set(String key,Object value){
        getRedisTemplate().opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    public void set(String key,String value){
        getRedisTemplate().opsForValue().set(key, value);
    }

    public void set(String key, String value, Duration duration){
        getRedisTemplate().opsForValue().set(key, value,duration);
    }
    public void set(String key, Object value, Long timeout, TimeUnit timeUnit){
        getRedisTemplate().opsForValue().set(key, JSONUtil.toJsonStr(value),timeout,timeUnit);
    }

    public <T> T  get(String key,Class<T> beanClass){
        String jsonString = getRedisTemplate().opsForValue().get(key);
        if (StrUtil.isEmpty(jsonString)){
            return null;
        }
        return JSONUtil.toBean(jsonString,beanClass);
    }

    public String  get(String key){
        return getRedisTemplate().opsForValue().get(key);
    }


    public void remove(String key){
        getRedisTemplate().delete(key);
    }


}
