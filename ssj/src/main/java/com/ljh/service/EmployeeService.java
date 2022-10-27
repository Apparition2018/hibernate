package com.ljh.service;

import com.ljh.entity.Employee;
import com.ljh.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * EmployeeService
 *
 * @author ljh
 * created on 2022/10/26 15:03
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public Page<Employee> page(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return employeeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Employee getByLastName(String lastName) {
        return employeeRepository.getByLastName(lastName);
    }

    @Transactional
    public void save(Employee employee) {
        if (employee.getId() == null) {
            employee.setCreateTime(new Date());
        }
        employeeRepository.saveAndFlush(employee);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> get(Integer id) {
        return employeeRepository.findById(id);
    }
}
