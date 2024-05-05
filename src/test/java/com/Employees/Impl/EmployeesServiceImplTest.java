package com.Employees.Impl;

import com.Employees.Mapper.EmployeesMapper;
import com.Employees.dto.DepartmentDto;
import com.Employees.dto.EmployeeDto;
import com.Employees.dto.EmployeeRequestDto;
import com.Employees.dto.Response;
import com.Employees.entity.EmployeeEntity;
import com.Employees.entity.EmployeesEntity;
import com.Employees.repository.EmployeeRepository;
import com.Employees.repository.EmployeesRepository;
import com.Employees.util.Constant;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class EmployeesServiceImplTest {
    @InjectMocks
    EmployeesServiceImpl employeesService;
    @Mock
    EmployeesMapper employeesMapper;
    @Mock
    EmployeesRepository employeesRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAllEmployees() {
        EmployeesEntity employee1 = new EmployeesEntity();
        employee1.setId(1L);
        employee1.setFirstName("mohamed");
        employee1.setLastName("salah");
        EmployeesEntity employee2 = new EmployeesEntity();
        employee2.setId(2L);
        employee2.setFirstName("salah");
        employee2.setLastName("mohamed");
        List<EmployeesEntity> employeesList = new ArrayList<>();
        employeesList.add(employee1);
        employeesList.add(employee2);
        when(employeesRepository.findAll()).thenReturn(employeesList);
        Response<List<EmployeeDto>> response = employeesService.getAllEmployees();
        assertEquals(2, response.getResult().size());

    }

    @Test
    public void testGetEmployee() {
        // Mock the behavior of employeesRepository
        Long employeeId = 1L;
        EmployeesEntity mockEntity = new EmployeesEntity();
        mockEntity.setId(employeeId);
        Mockito.when(employeesRepository.getEmployeesEntityById(employeeId)).thenReturn(mockEntity);
        Response<EmployeeDto> result = employeesService.getEmployee(employeeId);
        Assert.assertNotNull(result);

      }

    @Test
    public void testCreateEmployee() {
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().id(1L)
                .firstName("test")
                .created_by("test").
                lastName("salah")
                .lastModificationDate(LocalDateTime.now())
                .joiningDate(LocalDateTime.now())
                .department(new DepartmentDto(1,"silicon")).build();

         EmployeesEntity employeesEntity=new EmployeesEntity();
         EmployeeDto  employeeDto=new EmployeeDto();
         Mockito.when(employeesMapper.toEntity(Mockito.any(), Mockito.any())).thenReturn(employeesEntity);
         Mockito.when(employeesMapper.toDto(Mockito.any())).thenReturn(employeeDto);
         EmployeeEntity mockEntity = new EmployeeEntity();
         Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(mockEntity);
         Response<String> result = employeesService.createEmployee(employeeRequestDto);
         Assert.assertNotNull(result);
         Assert.assertEquals(Constant.EMPLOYEE_IS_CREATED, result.getResult());

      }

    @Test
    public void testDeleteEmployee() {
         Long employeeId = 1L;
        EmployeesEntity mockEntity = new EmployeesEntity();
        Mockito.when(employeesRepository.getEmployeesEntityById(employeeId)).thenReturn(mockEntity);

         Response<String> result = employeesService.deleteEmployee(employeeId);

         Assert.assertNotNull(result);
        Assert.assertEquals(Constant.EMPLOYEE_IS_DELETED, result.getResult());

      }

    @Test
    public void testEditEmployee() {
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().id(1L)
                .firstName("test")
                .created_by("test").
                lastName("salah")
                .lastModificationDate(LocalDateTime.now())
                .joiningDate(LocalDateTime.now())
                .department(new DepartmentDto(1,"silicon")).build();
         EmployeesEntity mockEntity = new EmployeesEntity();
         Mockito.when(employeesRepository.getEmployeesEntityById(employeeRequestDto.getId())).thenReturn(mockEntity);
         Response<EmployeeDto> result = employeesService.editEmployee(employeeRequestDto);
         Assert.assertNotNull(result);

         Assert.assertEquals(employeeRequestDto.getFirstName(), mockEntity.getFirstName());
         Assert.assertEquals(employeeRequestDto.getLastName(), mockEntity.getLastName());

      }


}
