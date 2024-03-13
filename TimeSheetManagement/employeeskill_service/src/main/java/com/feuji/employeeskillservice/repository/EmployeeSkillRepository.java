package com.feuji.employeeskillservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;

import jakarta.transaction.Transactional;
@Transactional
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkillEntity,Long>
{
//	@Query(value="select * from employee_skills where uuid=:employeeUUID",nativeQuery=true)
//	List<Optional<EmployeeSkillEntity>> findByEmployeeId(Long employeeId);
    Optional<EmployeeSkillEntity> findByUuid(String uuid);
}
