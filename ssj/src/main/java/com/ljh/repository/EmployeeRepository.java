package com.ljh.repository;

import com.ljh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EmployeeRepository
 *
 * @author ljh
 * created on 2022/10/26 15:02
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
