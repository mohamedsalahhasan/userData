package com.employees.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "FIRST_NAME", length = 45)
    private String firstName;

    @Size(max = 50)
    @Column(name = "LAST_NAME", length = 45)
    private String lastName;

    @NotNull
    @Column(name = "JOINING_DATE", nullable = false)
    private LocalDateTime joiningDate;

    @Size(max = 50)
    @Column(name = "CREATED_BY", length = 45)
    private String created_by;
    @Size(max = 50)
    @Column(name = "LAST_MODIFICATION_BY", length = 45)
    private String lastModificationBy;

    @NotNull
    @Column(name = "LAST_MODIFICATION_DATE", nullable = false)
    private LocalDateTime lastModificationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
}
