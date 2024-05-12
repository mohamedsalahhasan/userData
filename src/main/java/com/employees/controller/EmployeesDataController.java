package com.employees.controller;


import com.employees.dto.EmployeeDto;
import com.employees.dto.EmployeeRequestDto;
import com.employees.dto.Response;
import com.employees.exception.BusinesstException;
import com.employees.service.EmployeesService;
import com.employees.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/employees-data")
public class EmployeesDataController {
    private final Logger logger =  LoggerFactory.getLogger(EmployeesDataController.class);

    @Autowired(required=true)
    EmployeesService employeesService;
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description =  "return get all Employees data "),
            @ApiResponse(responseCode ="500", description =  "something wrong during get all Employees data")})
    @Parameters({
            @Parameter(name = "EmployeeDto", required = true)})
    @GetMapping("/employees")
    public ResponseEntity<Response<List<EmployeeDto>>>  getAllEmployees(){
        try {
             Response<List<EmployeeDto>> responseBody = employeesService.getAllEmployees();
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }catch (Exception e){
            logger.error("error during get All Employees",e);
            Response<List<EmployeeDto>> responseBody = new Response<>();
            responseBody.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description =  "return get Employees data "),
            @ApiResponse(responseCode ="500", description =  "something wrong during get Employees data")})
    @Parameters({
            @Parameter(name = "employeeId", required = true)
    })
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Response<EmployeeDto>>  getEmployee(@PathVariable Long employeeId){
        try {
            Response<EmployeeDto> responseBody = employeesService.getEmployee(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }catch (Exception e){
            logger.error("error during get Employee",e);
            Response<EmployeeDto> responseBody = new Response<>();
            responseBody.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
    @Operation(summary =  "return Value representation for created Employee")
    @Parameters({
            @Parameter(name = "employeeRequestDto",  required = true) })
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description =  "return created employee"),
            @ApiResponse(responseCode ="500", description =  "something wrong during create employee")})
    @PostMapping("/Add-employee")
    public ResponseEntity<Response<String>> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto){
        try {
             Response<String> responseBody = employeesService.createEmployee(employeeRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }catch (BusinesstException e){
            logger.error(Constant.ERROR_DURING_CREATE_EMPLOYEE,e);
            Response<String> responseBody = new Response<>();
            responseBody.setErrorMessage(e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(responseBody);
        }catch (Exception e){
            logger.error(Constant.ERROR_DURING_CREATE_EMPLOYEE,e);
            Response<String> responseBody = new Response<>();
            responseBody.setErrorMessage(Constant.ERROR_DURING_CREATE_EMPLOYEE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Operation(summary =  "Delete Employee by id")
    @Parameters({
            @Parameter(name = "employeeId", required = true) })
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description =  "return deleted custom employee Id"),
            @ApiResponse(responseCode ="500", description =  "something wrong during delete employee")})
    @DeleteMapping("/delete-Employee/{employeeId}")
    public ResponseEntity<Response<String>> deleteEmployee(@PathVariable Long employeeId){
        try {
            Response<String> responseBody = employeesService.deleteEmployee(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }catch (BusinesstException e){
            logger.error(Constant.ERROR_DURING_DELETE_EMPLOYEE,e);
            Response<String> responseBody = new Response<>();
            responseBody.setErrorMessage(e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(responseBody);
        }catch (Exception e){
            logger.error(Constant.ERROR_DURING_DELETE_EMPLOYEE,e);
            Response<String> responseBody = new Response<>();
            responseBody.setErrorMessage(Constant.ERROR_DURING_DELETE_EMPLOYEE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

    }
    @PutMapping("/edit-employee")
    public ResponseEntity<Response<EmployeeDto>> editEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        try {

            Response<EmployeeDto> response = employeesService.editEmployee(employeeRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (BusinesstException e) {
            logger.error(Constant.UPDATE_Employee_INTERNAL_SERVER_ERROR,e);
            Response<EmployeeDto> response = new Response<>();
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(response);
        } catch (Exception e) {
            logger.error(Constant.UPDATE_Employee_INTERNAL_SERVER_ERROR,e);
            Response<EmployeeDto> responseBody = new Response<>();
            responseBody.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    }
