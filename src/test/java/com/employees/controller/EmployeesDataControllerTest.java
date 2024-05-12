package com.employees.controller;


import com.employees.service.EmployeesService;
import com.employees.dto.DepartmentDto;
import com.employees.dto.EmployeeDto;
import com.employees.dto.EmployeeRequestDto;
import com.employees.dto.Response;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EmployeesDataControllerTest {
    @Mock
    EmployeesService employeesService;
    @InjectMocks
    private EmployeesDataController employeesDataController  ;
    @BeforeMethod
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployees(){
        ResponseEntity<Response<List<EmployeeDto>>> response = employeesDataController.getAllEmployees();
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void getAllEmployees_Exception(){
        Mockito.when(employeesService.getAllEmployees()).thenThrow(new RuntimeException("ERROR"));
        ResponseEntity<Response<List<EmployeeDto>>> response = employeesDataController.getAllEmployees();
        assertTrue(response.getStatusCode().is5xxServerError());
        assertEquals("ERROR", response.getBody().getErrorMessage());
    }

    @Test
    public void getEmployee(){
        ResponseEntity<Response<EmployeeDto>> response = employeesDataController.getEmployee(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getEmployee_Exception(){
        Mockito.when(employeesService.getEmployee(1L)).thenThrow(new RuntimeException("ERROR"));
        ResponseEntity<Response<EmployeeDto>> response = employeesDataController.getEmployee(1L);
        assertTrue(response.getStatusCode().is5xxServerError());
        assertEquals("ERROR", response.getBody().getErrorMessage());
    }

    @Test
    public void createEmployee(){
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().id(1L)
                .firstName("test")
                .created_by("test").
                lastName("salah")
                .lastModificationDate(LocalDateTime.now())
                .joiningDate(LocalDateTime.now())
                .department(new DepartmentDto(1,"silicon")).build();
         ResponseEntity<Response<String>> response = employeesDataController.createEmployee(employeeRequestDto);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void createEmployee_Exception(){
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().id(1L)
                .firstName("test")
                .created_by("test").
                lastName("salah")
                .lastModificationDate(LocalDateTime.now())
                .joiningDate(LocalDateTime.now())
                .department(new DepartmentDto(1,"silicon")).build();
        Mockito.when(employeesService.createEmployee(employeeRequestDto)).thenThrow(new RuntimeException("ERROR"));

        ResponseEntity<Response<String>> response = employeesDataController.createEmployee(employeeRequestDto);
        assertTrue(response.getStatusCode().is5xxServerError());
    }

    @Test
    public void deleteEmployee(){
        ResponseEntity<Response<String>> response = employeesDataController.deleteEmployee(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deleteEmployee_Exception(){
        Mockito.when(employeesService.deleteEmployee(1L)).thenThrow(new RuntimeException("ERROR"));
        ResponseEntity<Response<String>> response = employeesDataController.deleteEmployee(1L);
        assertTrue(response.getStatusCode().is5xxServerError());
     }
    @Test
    public void editEmployee(){
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().id(1L)
                .firstName("test")
                .created_by("test").
                lastName("salah")
                .lastModificationDate(LocalDateTime.now())
                .joiningDate(LocalDateTime.now())
                .department(new DepartmentDto(1,"silicon")).build();
        ResponseEntity<Response<EmployeeDto>> response = employeesDataController.editEmployee(employeeRequestDto);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void editEmployee_Exception(){
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().id(1L)
                .firstName("test")
                .created_by("test").
                lastName("salah")
                .lastModificationDate(LocalDateTime.now())
                .joiningDate(LocalDateTime.now())
                .department(new DepartmentDto(1,"silicon")).build();
        Mockito.when(employeesService.editEmployee(employeeRequestDto)).thenThrow(new RuntimeException("ERROR"));

        ResponseEntity<Response<EmployeeDto>> response = employeesDataController.editEmployee(employeeRequestDto);
        assertTrue(response.getStatusCode().is5xxServerError());
    }
}
