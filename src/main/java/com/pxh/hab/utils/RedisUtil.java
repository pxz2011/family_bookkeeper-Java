//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.utils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class RedisUtil {
    @Autowired
    //2.添加静态的变
    private RedisTemplate<String,String> redisTemplate;


    //1....其他的工具方法...
    public void set(String key, String value, long date) {
        redisTemplate.opsForValue().set(key, value, date, TimeUnit.DAYS);
    }

    public String get(String key) {
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    public boolean isExists(String key) {
        return Objects.equals(Boolean.TRUE,redisTemplate.hasKey(key));
    }

    public void del(String key){
        redisTemplate.delete(key);
    }
}
