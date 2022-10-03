package com.backend.service;

import java.util.List;

import com.backend.entity.Customer;

public interface CustomerService {
    List<Customer> getAll();

    List<Customer> getAll(String sortBy, boolean isAsc);

    List<Customer> getAll(String sortProperty, boolean sortDirection, int offset, int recordsPerPage);

    int countTotalRecords();

    List<Customer> search(String keyword);

    Customer get(int customerId);

    void save(Customer customer);

    void delete(int id);




}