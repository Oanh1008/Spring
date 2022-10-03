package com.redis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductH {
    @JsonProperty("key")
    private String key;

    @JsonProperty("product")
    private Product product;
}
