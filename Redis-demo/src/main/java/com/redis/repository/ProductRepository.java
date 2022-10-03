package com.redis.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.redis.entity.Product;
import com.redis.util.RedisUtil;

@Repository
public class ProductRepository {
    public static final String PRODUCT_LIST = "friends";

    @Autowired
    private RedisUtil redisUtil;

    private RedisTemplate redisTemplate;

    public ProductRepository(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


//    public Product save(Product product) {
//        redisTemplate.opsForList().rightPush(PRODUCT_LIST, product);
//        return product;
//    }

    public  List<Object> getAll() {
        return redisUtil.lGet(PRODUCT_LIST,0,-1);
    }
    public boolean set(String value){
        return redisUtil.set(PRODUCT_LIST,value);
    }
    public Object get(){
        return redisUtil.get(PRODUCT_LIST);
    }

//    public Product get(int i) {
//        return (Product) redisTemplate.opsForList().index(PRODUCT_LIST, i);
//    }
//
//    public Product deleteR() {
//        return (Product) redisTemplate.opsForList().rightPop(PRODUCT_LIST);
//    }
//
//    public Product deleteL() {
//        return (Product) redisTemplate.opsForList().leftPop(PRODUCT_LIST);
//    }
//
//    public long delete(int i) {
//        return redisTemplate.opsForList().remove(PRODUCT_LIST, 1, get(i));
//    }

    public static void main(String[] args) {
        String a = "\"064\" \"\"";
        System.out.println(a);
    }
}
