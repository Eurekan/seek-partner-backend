package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test(){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("yupaoString","yupao");
        valueOperations.set("yupaoInt", 1);
        valueOperations.set("yupaoDouble", 2.0);
        User user = new User();
        user.setId(1L);
        valueOperations.set("yupaoUser", user);

        // 查
        Object yupao = valueOperations.get("yupaoString");
        Assertions.assertEquals("yupao", yupao);
        yupao = valueOperations.get("yupaoInt");
        Assertions.assertEquals(1L, (int) (Integer) yupao);
        yupao = valueOperations.get("yupaoDouble");
        Assertions.assertEquals(2.0, (double) (Double) yupao);
        System.out.println(valueOperations.get("yupaoUser"));

        // 改
        valueOperations.set("yupaoString", "Eureka");

        // 删
        redisTemplate.delete("yupaoString");
    }
}
