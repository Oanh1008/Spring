package com.redis.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.redis.entity.Product;
import com.redis.entity.ProductH;
import com.redis.util.RedisUtil;

@Repository
public class HashRepository {
    public static final String HASH_K = "HK";

    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;


    public HashRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setH(ProductH productH) {
        redisUtil.hSet(HASH_K,productH.getKey(),productH.getProduct());
    }

    public Map<String, Product> getALl() {
        return redisTemplate.opsForHash().entries(HASH_K);
    }
}
