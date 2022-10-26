package com.ljh.controller;

import com.ljh.entity.Employee;
import com.ljh.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public EmployeeHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/emps")
    public String list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr, Map<String, Object> map) {
        int pageNo;

        try {
            pageNo = Integer.parseInt(pageNoStr);
            if (pageNo < 1) {
                pageNo = 1;
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        Page<Employee> page = employeeService.getPage(pageNo, 5);
        map.put("page", page);

        return "emp/list";
    }
}
