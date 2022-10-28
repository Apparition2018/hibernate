package com.ljh.controller;

import com.ljh.entity.Employee;
import com.ljh.service.DepartmentService;
import com.ljh.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * EmployeeHandler
 *
 * @author ljh
 * created on 2022/10/26 15:08
 */
@Controller
public class EmployeeHandler {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeHandler(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/emps")
    public String list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr, Map<String, Object> map) {
        int pageNo = 1;

        try {
            pageNo = Integer.parseInt(pageNoStr);
            if (pageNo < 1) {
                pageNo = 1;
            }
        } catch (NumberFormatException ignored) {
        }

        Page<Employee> page = employeeService.page(pageNo, 5);
        map.put("page", page);

        return "emp/list";
    }

    @GetMapping("/emp")
    public String input(Map<String, Object> map) {
        map.put("employee", new Employee());
        map.put("depts", departmentService.getAll());
        return "emp/input";
    }

    @ResponseBody
    @PostMapping("ajaxValidateLastName")
    public String validateLastName(@RequestParam(value = "lastName", required = true) String lastName) {
        Employee employee = employeeService.getByLastName(lastName);
        if (employee == null) {
            return "0";
        } else {
            return "1";
        }
    }

    @PostMapping("/emp")
    public String save(Employee employee) {
        employeeService.save(employee);
        return "redirect:/emps";
    }

    @PutMapping("/emp/{id}")
    public String update(Employee employee) {
        employeeService.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
        Employee employee = employeeService.get(id).orElseThrow(null);
        map.put("employee", employee);
        map.put("depts", departmentService.getAll());
        return "emp/input";
    }

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            Employee employee = employeeService.get(id).orElseThrow(null);
            employee.setDept(null);
            map.put("employee", employee);
        }
    }

    @DeleteMapping(value = "/emp/{id}")
    public String delete(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return "redirect:/emps";
    }
}
