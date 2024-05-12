package com.employees.mapper;

import com.employees.dto.EmployeeDto;
import com.employees.dto.EmployeeRequestDto;
import com.employees.entity.EmployeesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {
    EmployeesMapper INSTANCE = Mappers.getMapper(EmployeesMapper.class);
    EmployeeDto toDto(EmployeesEntity entity);
    EmployeesEntity toEntity(EmployeeRequestDto dto, @MappingTarget EmployeesEntity entity);




}
