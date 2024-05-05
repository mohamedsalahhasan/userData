package com.Employees.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class EmployeeRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String created_by;
    private String lastModificationBy;
    private LocalDateTime joiningDate;
    private DepartmentDto department;
    private LocalDateTime lastModificationDate;
}
