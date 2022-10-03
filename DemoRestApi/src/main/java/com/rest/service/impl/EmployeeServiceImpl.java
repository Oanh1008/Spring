package com.rest.service.impl;

import com.rest.exception.ResourceNotFoundException;
import com.rest.model.Employee;
import com.rest.repository.EmployeeRepository;
import com.rest.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    /**
     * Long way :
     *         Optional<Employee> employee = employeeRepository.findById(id);
     *         if(employee.isPresent()){
     *             return employee.get();
     *         }else{
     *             throw new ResourceNotFoundException("Employee","Id",id);
     *        }
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public Employee getById(long id) {
        return employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee","Id",id));
    }

    @Override
    public Employee update(Employee employee, long id) {
        Optional<Employee> existEmployee= employeeRepository.findById(id);
        if(existEmployee.isPresent()){
            Employee saveEmployee = existEmployee.get();
            saveEmployee.setFirstName(employee.getFirstName());
            saveEmployee.setLastName(employee.getLastName());
            saveEmployee.setEmail(employee.getEmail());
            return employeeRepository.save(saveEmployee);
        }else{
            throw  new ResourceNotFoundException("Employee","Id",id);
        }
    }

    /**
     * check id is existed in DB
     * @param id
     */
    @Override
    public void delete(long id) {
        employeeRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Employee","id",id));
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updatePatch(long id, Map<Object, Object> fields) {
        Optional<Employee> existEmployee = employeeRepository.findById(id);
        if (existEmployee.isPresent()) {
            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(Employee.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existEmployee.get(), v);
            });
            return employeeRepository.save(existEmployee.get());
        }else {
            throw new ResourceNotFoundException("Employee","Id",id);
        }
    }

    public static void main(String[] args) {
        Integer test = 6;
        System.out.println(test.toString());
    }
}
