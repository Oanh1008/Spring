package com.rest.controller;

import com.rest.model.Employee;
import com.rest.service.EmployeeService;
import org.apache.el.util.ReflectionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    // new Employee
    @PostMapping
    public ResponseEntity<Employee> save( @RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }
    // get all
    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getAll();
    }
    // get employee by id
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        return new ResponseEntity<>(employeeService.getById(id),HttpStatus.OK);
    }

    // update employee
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable("id") Long id,
                                                       @RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.update(employee,id),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") long id){
        employeeService.delete(id);
        return new ResponseEntity<>("Delete successfully",HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Employee> updatePatch(@PathVariable("id") long id, @RequestBody Map<Object,Object> fields){
        return new ResponseEntity<>(employeeService.updatePatch(id,fields),HttpStatus.OK);
    }




}
