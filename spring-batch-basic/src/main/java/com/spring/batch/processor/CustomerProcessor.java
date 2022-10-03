package com.spring.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.spring.batch.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        return customer;
    }
}
