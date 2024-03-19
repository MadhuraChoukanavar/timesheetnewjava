package com.feuji.accountprojectservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.accountprojectservice.dto.AccountDto;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;

public interface AccountProjectsRepo extends JpaRepository<AccountProjectsEntity,Integer>{
	
	Optional<AccountProjectsEntity> findByUuid(String uuid);
	
	@Query("SELECT NEW com.feuji.accountprojectservice.dto.AccountDto(" +
	         " ap.accountProjectId ,ap.projectPId, " +
	           "ap.projectName, " +
	           "a.accountName, " +
	           "e.firstName, " +
	           "rd.referenceDetailValue,ap.noOfBillingHours,ap.plannedStartDate,ap.plannedEndDate,ap.actualStartDate,ap.actualEndDate) " +
	           "FROM AccountProjectsEntity ap " +
	           "JOIN AccountEntity a ON ap.accountId = a.accountId " +
	           "JOIN EmployeeEntity e ON ap.projectManagerId = e.employeeId " +
	           "JOIN CommonReferenceDetailsEntity rd ON ap.priority = rd.referenceDetailId  " + 
	           "JOIN CommonReferenceTypeEntity rt ON rd.referenceTypeId = rt.referenceTypeId " +
	           "WHERE rt.referenceTypeId = :referenceTypeId")
	List<AccountDto> accountProjectDto(@Param("referenceTypeId") Integer referenceTypeId);
	
//	@Query(value="select account_project_id from account_projects ap join account a  on ap.account_id=a.account_id	where a.account_id=2",nativeQuery=true)
//	List<AccountProjectsEntity> getAccountProjectIdByAccountId( Integer accountId);
	
//	@Query(value="select account_project_id from account_projects ap where account_id=(select account_id from account where account_id=?",nativeQuery=true)
//	List<Integer> getAccountProjectIdByAccountId(Integer referenceTypeId);
	
	@Query(value="select account_project_id from account_projects ap where account_id=(select account_id from account where account_id=:accountId)", nativeQuery=true)
	List<Integer> getAccountProjectIdByAccountId(@Param("accountId") Integer accountId);

	
	@Modifying
	@Query(value="update account_projects set is_deleted=1 where account_project_id=:accountProjectId", nativeQuery=true)
	void updateIsDeleted(Integer accountProjectId);


    

}
