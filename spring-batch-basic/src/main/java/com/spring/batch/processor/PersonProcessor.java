package com.spring.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.spring.batch.entity.Person;

public class PersonProcessor implements ItemProcessor<Person,Person> {

    @Override
    public Person process(Person person) throws Exception {
        return person;
    }
}
