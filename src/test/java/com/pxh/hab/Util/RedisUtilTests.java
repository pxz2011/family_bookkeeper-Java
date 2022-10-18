package com.pxh.hab.Util;

import com.pxh.hab.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
public class RedisUtilTests {
    @Test
    public void conn(){
        Jedis jedis = new Jedis("localhost");
        jedis.auth("root");

        jedis.set("token","123");
        System.out.println(jedis.get("site"));

    }
}
