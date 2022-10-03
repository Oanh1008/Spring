package com.redis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redis.entity.Product;
import com.redis.entity.ProductH;
import com.redis.repository.HashRepository;

@RestController
public class HashController {

    @Autowired
    private HashRepository hashRepository;

    @PostMapping()
    public void set(@RequestBody ProductH productH){
        hashRepository.setH(productH);
    }
    @GetMapping
    public Map<String, Product> getAll(){
        return hashRepository.getALl();
    }
}
