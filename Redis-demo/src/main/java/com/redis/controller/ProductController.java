package com.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.entity.Product;
import com.redis.repository.ProductRepository;

import lombok.Getter;

@RestController
@RequestMapping("/redis")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

//    @PostMapping
//    public com.redis.entity.Product save(@RequestBody com.redis.entity.Product product) {
//        return productRepository.save(product);
//    }

    @GetMapping
    public List<Object> getAll() {
        return  productRepository.getAll();
    }
    @PostMapping
    public boolean set(@RequestBody String value){
        return productRepository.set(value);
    }
    @GetMapping("/get")
    public Object get(){
        return productRepository.get();
    }

//    @DeleteMapping("/l")
//    public Product deleteL() {
//        return productRepository.deleteL();
//    }
//
//    @DeleteMapping("/r")
//    public Product deleteR() {
//        return productRepository.deleteR();
//    }
//
//    @DeleteMapping("/{index}")
//    public long delete(@PathVariable("index") int i) {
//        return productRepository.delete(i);
//    }
//
//    @GetMapping("/{index}")
//    public Product get(@PathVariable("index") int i) {
//        return productRepository.get(i);
//    }
}
