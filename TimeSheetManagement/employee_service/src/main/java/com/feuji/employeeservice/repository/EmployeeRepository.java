package com.feuji.employeeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>{

	
	boolean existsByEmployeeCode(String empCode);

	@Query("SELECT e.reportingManagerId FROM EmployeeEntity e WHERE e.employeeId = :employeeId")
	public Integer getReportingMngIdByEmpId(@Param("employeeId") Integer employeeId);

	@Query("SELECT e FROM EmployeeEntity e WHERE e.uuid = :uuid")
	EmployeeEntity findByUuid(@Param("uuid") String uuid);


	@Query("SELECT new com.feuji.employeeservice.dto.EmployeeDto(ule.userEmpId, empe.employeeCode, empe.firstName, empe.middleName, empe.lastName, empe.image, empe.designation, empe.email, empe.gender, empe.dateOfJoining,empe.reportingManagerId, empe.employmentType, empe.status, empe.deliveryUnitId, empe.businessUnitId) "
	        + "FROM UserLoginEntity ule left join EmployeeEntity empe on( ule.userEmpId=empe.employeeId)"
	        + "WHERE ule.userEmpId = :userEmpId")
	List<EmployeeDto> getEmployeeDetailsByUserEmpId(@Param("userEmpId") Integer userEmpId);
	
//	 List<AddEmployee> getByReferenceTypeID(Integer referenceTypeId);
//	
//	@Query("SELECT e FROM AddEmployee e WHERE e.referenceTypeId = :referenceTypeId")
	@Query("SELECT new com.feuji.employeeservice.dto.AddEmployee(crde.referenceDetailId, crde.referenceDetailValue,crte.referenceTypeId) "
			+ "FROM CommonReferenceDetailsEntity crde "
			+ "JOIN CommonReferenceTypeEntity crte ON crde.referenceType.referenceTypeId = crte.referenceTypeId "
			+ "WHERE crde.referenceType.referenceTypeId = :referenceTypeId")
    List<AddEmployee> getByReferenceTypeId(Integer referenceTypeId);
//	
//	ADD EMPLOYEE ALL QUERY

	@Query("SELECT new com.feuji.employeeservice.dto.AddEmployee(crde.referenceDetailId, crde.referenceDetailValue) "
			+ "FROM CommonReferenceDetailsEntity crde "
			+ "JOIN CommonReferenceTypeEntity crte ON crde.referenceType.referenceTypeId = crte.referenceTypeId "
			+ "WHERE crde.referenceType.referenceTypeId = :referenceTypeId")
	List<AddEmployee> getGender(@Param("referenceTypeId") Integer referenceTypeId);
	
	@Query("SELECT new com.feuji.employeeservice.dto.AddEmployee(crde.referenceDetailId, crde.referenceDetailValue) "
			+ "FROM CommonReferenceDetailsEntity crde "
			+ "JOIN CommonReferenceTypeEntity crte ON crde.referenceType.referenceTypeId = crte.referenceTypeId "
			+ "WHERE crde.referenceType.referenceTypeId = :referenceTypeId")
	List<AddEmployee> getEmploymentType(@Param("referenceTypeId") Integer referenceTypeId);
	
	@Query("SELECT new com.feuji.employeeservice.dto.AddEmployee(crde.referenceDetailId, crde.referenceDetailValue) "
			+ "FROM CommonReferenceDetailsEntity crde "
			+ "JOIN CommonReferenceTypeEntity crte ON crde.referenceType.referenceTypeId = crte.referenceTypeId "
			+ "WHERE crde.referenceType.referenceTypeId = :referenceTypeId")
	List<AddEmployee> getDesignation(@Param("referenceTypeId") Integer referenceTypeId);
	
	@Query("SELECT new com.feuji.employeeservice.dto.AddEmployee(crde.referenceDetailId, crde.referenceDetailValue) "
			+ "FROM CommonReferenceDetailsEntity crde "
			+ "JOIN CommonReferenceTypeEntity crte ON crde.referenceType.referenceTypeId = crte.referenceTypeId "
			+ "WHERE crde.referenceType.referenceTypeId = :referenceTypeId")
	List<AddEmployee> getBusinessUnit(@Param("referenceTypeId") Integer referenceTypeId);


}