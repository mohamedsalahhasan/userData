package com.employees.repository;

import com.employees.entity.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, Long> {

    EmployeesEntity getEmployeesEntityById(Long employeeId);
    List<EmployeesEntity> findAll();

}
