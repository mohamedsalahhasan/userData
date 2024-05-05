package com.Employees.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID", nullable = false)
    private Integer departmentId;
    @Size(max = 50)
    @Column(name = "DEPARTMENT_NAME", length = 45)
    private String departmentName;
}
