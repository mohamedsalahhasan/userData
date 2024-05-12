package com.employees.service;



import com.employees.dto.EmployeeDto;
import com.employees.dto.EmployeeRequestDto;
import com.employees.dto.Response;

import java.util.List;

public interface EmployeesService {
    public Response<List<EmployeeDto>> getAllEmployees();

    Response<EmployeeDto> getEmployee(Long employeeId);

    Response<String> createEmployee(EmployeeRequestDto employeeRequestDto);

    Response<String> deleteEmployee(Long employeeId);

    Response<EmployeeDto> editEmployee(EmployeeRequestDto employeeRequestDto);
}
