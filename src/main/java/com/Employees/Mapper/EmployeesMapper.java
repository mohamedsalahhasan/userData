package com.Employees.Mapper;

import com.Employees.dto.EmployeeDto;
import com.Employees.dto.EmployeeRequestDto;
import com.Employees.entity.EmployeesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {
    EmployeesMapper INSTANCE = Mappers.getMapper(EmployeesMapper.class);
//    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto toDto(EmployeesEntity entity);
    EmployeesEntity toEntity(EmployeeRequestDto dto, @MappingTarget EmployeesEntity entity);




}
