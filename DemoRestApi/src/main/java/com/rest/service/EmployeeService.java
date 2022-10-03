package com.rest.service;

import com.rest.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getAll();

    Employee getById(long id) ;

    Employee update(Employee employee,long id);

    void delete(long id);

    Employee updatePatch(long id, Map<Object,Object> fields);
}
