package com.employees.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
     private String created_by;
    private String lastModificationBy;
    private LocalDateTime joiningDate;
    private DepartmentDto department;
    private LocalDateTime lastModificationDate;

}
