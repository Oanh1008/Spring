package com.backend.controller;

import java.util.List;

import com.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entity.Customer;
import com.backend.exception.handler.EntityNotFoundException;
import com.backend.pagination.Pageable;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final int CONFIRURABLE_RECORDS_PER_PAGE = 6;

    // GET /customers?sort=firstName,desc&page=2
    @GetMapping("/customers")
    public Pageable<Customer> getCustomersWithSorting(
            @RequestParam(value = "sort", defaultValue = "firstName,asc") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        // SORTING
        String sortProperty = sort;
        Boolean sortDirection = true;
        if (sort != null && sort.contains(",")) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                sortProperty = sortParams[0];
                sortDirection = sortParams[1].equalsIgnoreCase("asc");
            }
        }

        // PAGINATION
        // Step 1: count total records
        int totalRecords = customerService.countTotalRecords();
        int recordsPerPage = CONFIRURABLE_RECORDS_PER_PAGE;

        // Step 2: calculate total pages - need to return webapp
        int totalPages = (int) Math.ceil((float) totalRecords / recordsPerPage);

        // Step 3: calculate offset, row count = recordsPerPage
        int offset = (page - 1) * recordsPerPage;

        return new Pageable<>(
                customerService.getAll(sortProperty, sortDirection, offset, recordsPerPage),
                totalPages
        );
    }

    @GetMapping("/customers/total-records")
    public int countTotalRecords() {
        return customerService.countTotalRecords();
    }

    // NOW: GET http://localhost:8080/08-spring-crm-rest-api/api/customers/filter?search=david
    @GetMapping("/customers/filter")
    public List<Customer> search(@RequestParam("search") String keyword) {
        return customerService.search(keyword);
    }


    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId) {
        Customer customer = customerService.get(customerId);
        if (customer == null) {
            throw new EntityNotFoundException("Customer id not found - " + customerId);
        }
        return customer;
    }

    // end-point POST /customers - add new customer
    @PostMapping("/customers")
    public Customer add(@RequestBody Customer customer) {
        // JSON can have ID(non-null, non-zero) or null valid
        // Override id=0 to make sure with POST method we will ignore ID and always add
        // new customer
        customer.setId(0);
        customerService.save(customer);
        return customer;
    }

    // end-point PUT /customers - update existing customer

    @PutMapping("/customers")
    public Customer update(@RequestBody Customer customer) {
        customerService.save(customer);
        return customer;

    }

    // end-point DELETE /customers/{customerId} - delete customer
    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        Customer tempCustomer = customerService.get(customerId);
        if (tempCustomer == null) {
            throw new EntityNotFoundException("Customer id not found - " + customerId);
        }
        customerService.delete(customerId);
        return "Deleted customer id - " + customerId;
    }

}