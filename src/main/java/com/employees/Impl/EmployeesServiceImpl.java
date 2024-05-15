package com.employees.Impl;


import com.employees.exception.EmployeeAlreadyExistsException;
import com.employees.exception.EmployeeGlobalException;
import com.employees.exception.EmployeeNotFoundException;
import com.employees.mapper.EmployeesMapper;
import com.employees.dto.DepartmentDto;
import com.employees.dto.EmployeeDto;
import com.employees.dto.EmployeeRequestDto;
import com.employees.dto.Response;
import com.employees.entity.Department;
 import com.employees.entity.EmployeeEntity;
import com.employees.entity.EmployeesEntity;
import com.employees.exception.BusinesstException;
import com.employees.repository.EmployeeRepository;
import com.employees.repository.EmployeesRepository;
import com.employees.service.EmployeesService;
import com.employees.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeesServiceImpl implements EmployeesService {
    private final Logger logger =  LoggerFactory.getLogger(EmployeesServiceImpl.class);
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeesMapper employeesMapper;
    @Override
    public Response<List<EmployeeDto>> getAllEmployees() {
        long startTime=System.currentTimeMillis();
        List<EmployeesEntity>  employeesList=employeesRepository.findAll();
        logger.info("end get All Employees, elapsedTime: {} ms",System.currentTimeMillis()-startTime);
         Response<List<EmployeeDto>>  employeeDtoList=getCloneAllEmployees(employeesList);
        return employeeDtoList;
    }

    @Override
    public Response<EmployeeDto> getEmployee(Long employeeId) {
        long startTime=System.currentTimeMillis();
        Response<EmployeeDto> response = new Response<>();
        EmployeesEntity  employeesEntity=employeesRepository.getEmployeesEntityById(employeeId);
        if(employeesEntity==null){
            throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND_ERROR);
        }
        EmployeeDto employeeDto=convertEmployeesEntityToDto(employeesEntity);
        response.setResult(employeeDto);
        logger.info("end get Employee, elapsedTime: {} ms",System.currentTimeMillis()-startTime);
        return response;
    }

    @Override
    public Response<String> createEmployee(EmployeeRequestDto employeeRequestDto) {
        EmployeeEntity newCustomReport = EmployeeEntity.builder()
                .firstName(employeeRequestDto.getFirstName())
                .lastModificationBy(employeeRequestDto.getLastModificationBy())
                .created_by(employeeRequestDto.getCreated_by())
                .joiningDate(LocalDateTime.now())
                .lastModificationDate(LocalDateTime.now())
                .lastName(employeeRequestDto.getLastName())
                .department(convertDtoToDepartmentEntity(employeeRequestDto.getDepartment()))
                .build();
        long startTime=System.currentTimeMillis();
        EmployeeEntity employeeEntity  =employeeRepository.save(newCustomReport);
        logger.info("end get Create employee, elapsedTime: {} ms",System.currentTimeMillis()-startTime);
        Response<String>  response=new Response<>();


        if(employeeEntity ==null){
                throw new EmployeeAlreadyExistsException(Constant.ERROR_DURING_CREATE_EMPLOYEE);
        }
        response.setResult(Constant.EMPLOYEE_IS_CREATED);
        return response;
    }

    @Override
    public Response<String> deleteEmployee(Long employeeId) {

        EmployeesEntity  employeesEntity=employeesRepository.getEmployeesEntityById(employeeId);
        logger.info("Employee is Null : {}",employeesEntity == null);
        if (employeesEntity == null ) {
            throw new EmployeeAlreadyExistsException(Constant.EMPLOYEE_NOT_FOUND_ERROR);
        }
        try {
            long startTime=System.currentTimeMillis();
            employeesRepository.delete(employeesEntity);
            logger.info("end delete employee, elapsedTime: {} ms",System.currentTimeMillis()-startTime);

        }catch (Exception e){
            throw new EmployeeGlobalException(Constant.ERROR_DURING_DELETE_EMPLOYEE);
        }
        Response<String> response=new Response<>();
        response.setResult(Constant.EMPLOYEE_IS_DELETED);
        return response;
    }

    @Override
    public Response<EmployeeDto> editEmployee(EmployeeRequestDto employeeRequestDto) {
        EmployeesEntity  employeesEntity=employeesRepository.getEmployeesEntityById(employeeRequestDto.getId());
        logger.info("Employee is Null : {}",employeesEntity == null);
        if (employeesEntity == null ) {
            throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND_ERROR);
        }
        Response<EmployeeDto> response=new Response<>();
        try {

            employeesEntity.setFirstName(employeeRequestDto.getFirstName());
            employeesEntity.setLastName(employeeRequestDto.getLastName());
            long startTime=System.currentTimeMillis();
            response.setResult(convertEmployeesEntityToDto(employeesRepository.save(employeesEntity)));
            logger.info("end Edit employee, elapsedTime: {} ms",System.currentTimeMillis()-startTime);

        }catch (Exception e){
            throw new EmployeeGlobalException(Constant.ERROR_DURING_EDIT_EMPLOYEE);
        }
        return response;
    }

    private Department convertDtoToDepartmentEntity(DepartmentDto departmentDto) {
        Department department=Department.builder()
                .departmentId(departmentDto.getDepartmentId()).departmentName(departmentDto.getDepartmentName()).build();
        return department;
    }

    private Response<List<EmployeeDto>> getCloneAllEmployees(List<EmployeesEntity> employeesList) {
        Response<List<EmployeeDto>> response = new Response<>();
         List<EmployeeDto> employeeDtoList = employeesList.stream()
                .map(this::convertEmployeesEntityToDto)
                .collect(Collectors.toList());
        response.setResult(employeeDtoList);
        return  response;
    }

    private EmployeeDto convertEmployeesEntityToDto(EmployeesEntity employee) {


//        EmployeeDto employeeDto = new EmployeeDto();
//        employeeDto.setId(employee.getId());
//        employeeDto.setFirstName(employee.getFirstName());
//        employeeDto.setLastName(employee.getLastName());
//        employeeDto.setCreated_by(employee.getCreated_by());
//        employeeDto.setJoiningDate(employee.getJoiningDate());
//        employeeDto.setLastModificationBy(employee.getLastModificationBy());
//        employeeDto.setLastModificationDate(employee.getLastModificationDate());
//        employeeDto.setDepartment(convertDepartmentEntityToDto(employee.getDepartment()));
        return employeesMapper.toDto(employee);
    }

//    private DepartmentDto convertDepartmentEntityToDto(DepartmentEntity department) {
//        DepartmentDto departmentDto=new DepartmentDto();
//        departmentDto.setDepartmentId(department.getDepartmentId());
//        departmentDto.setDepartmentName(department.getDepartmentName());
//        return departmentDto;
//    }

}
