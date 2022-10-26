package com.ljh.repository;

import com.ljh.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DepartmentRepository
 *
 * @author ljh
 * created on 2022/10/27 1:27
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
