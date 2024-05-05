package com.Employees.Impl;


import com.Employees.Mapper.EmployeesMapper;
import com.Employees.dto.DepartmentDto;
import com.Employees.dto.EmployeeDto;
import com.Employees.dto.EmployeeRequestDto;
import com.Employees.dto.Response;
import com.Employees.entity.Department;
 import com.Employees.entity.EmployeeEntity;
import com.Employees.entity.EmployeesEntity;
import com.Employees.exception.BusinesstException;
import com.Employees.repository.EmployeeRepository;
import com.Employees.repository.EmployeesRepository;
import com.Employees.Service.EmployeesService;
import com.Employees.util.Constant;
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
            response.setErrorMessage(Constant.ERROR_DURING_CREATE_EMPLOYEE);
            throw new BusinesstException(Constant.ERROR_DURING_CREATE_EMPLOYEE, HttpStatus.BAD_REQUEST);
        }
        response.setResult(Constant.EMPLOYEE_IS_CREATED);
        return response;
    }

    @Override
    public Response<String> deleteEmployee(Long employeeId) {

        EmployeesEntity  employeesEntity=employeesRepository.getEmployeesEntityById(employeeId);
        logger.info("Employee is Null : {}",employeesEntity == null);
        if (employeesEntity == null ) {
            throw new BusinesstException(Constant.EMPLOYEE_NOT_FOUND_ERROR,HttpStatus.BAD_REQUEST);
        }
        try {
            long startTime=System.currentTimeMillis();
            employeesRepository.delete(employeesEntity);
            logger.info("end delete employee, elapsedTime: {} ms",System.currentTimeMillis()-startTime);

        }catch (Exception e){
            throw new BusinesstException(Constant.ERROR_DURING_DELETE_EMPLOYEE,HttpStatus.NOT_FOUND);
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
            throw new BusinesstException(Constant.EMPLOYEE_NOT_FOUND_ERROR,HttpStatus.BAD_REQUEST);
        }
        Response<EmployeeDto> response=new Response<>();
        try {

            employeesEntity.setFirstName(employeeRequestDto.getFirstName());
            employeesEntity.setLastName(employeeRequestDto.getLastName());
            long startTime=System.currentTimeMillis();
            response.setResult(convertEmployeesEntityToDto(employeesRepository.save(employeesEntity)));
            logger.info("end Edit employee, elapsedTime: {} ms",System.currentTimeMillis()-startTime);

        }catch (Exception e){
            throw new BusinesstException(Constant.ERROR_DURING_EDIT_EMPLOYEE,HttpStatus.NOT_FOUND);
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
