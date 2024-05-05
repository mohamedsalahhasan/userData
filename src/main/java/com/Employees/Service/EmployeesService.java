package com.Employees.Service;



import com.Employees.dto.EmployeeDto;
import com.Employees.dto.EmployeeRequestDto;
import com.Employees.dto.Response;

import java.util.List;

public interface EmployeesService {
    public Response<List<EmployeeDto>> getAllEmployees();

    Response<EmployeeDto> getEmployee(Long employeeId);

    Response<String> createEmployee(EmployeeRequestDto employeeRequestDto);

    Response<String> deleteEmployee(Long employeeId);

    Response<EmployeeDto> editEmployee(EmployeeRequestDto employeeRequestDto);
}
