package com.feuji.timesheetentryservice.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;


//import com.feuji.timesheetentryservice.entity.ProjectTimesheetSummaryView;
@EnableJpaRepositories
public interface TimesheetWeekSummaryRepo extends JpaRepository<TimesheetWeekSummaryViewEntity,Integer>{
//	
//	@Query("SELECT p FROM TimesheetWeekSummaryViewEntity p " +
//	           "WHERE p.approvedBy = :approvedBy " +
//	           "AND p.accountProjectId = :accountProjectId " +
//	           "AND p.weekNumber = :weekNumber")
//	    List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(
//	        @Param("approvedBy") Integer approvedBy,
//	        @Param("accountProjectId") Integer accountProjectId,
//	        @Param("weekNumber") Integer weekNumber
//	    );
	
 
	@Query("SELECT distinct NEW com.feuji.timesheetentryservice.dto.ProjectNameDto(pwt.accountId,ap.accountProjectId,ap.projectName) FROM TimesheetWeekEntity pwt JOIN AccountProjectsEntity ap ON pwt.accountProjectId = ap.accountProjectId WHERE pwt.accountId = :accountId")
	public List<ProjectNameDto> getAccountProjects(@Param("accountId") Integer accountId);

	
	@Query(
			"SELECT new com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto( "
			        + " pwts.timesheetWeekId , pwts.employeeId, pwts.accountId, pwts.accountProjectId, "
			        + " pwts.weekStartDate, pwts.weekEndDate, pwts.weekNumber, pdts.timesheetDayId,  "
			        + " aptt.taskId, aptt.taskTypeId, pdts.date, pdts.numberOfHours, pdts.attendanceType ) "
			        + " FROM AccountProjectTask aptt "
			        + " JOIN TimesheetDayEntity pdts ON aptt.taskId = pdts.taskId "
			        + " JOIN TimesheetWeekEntity pwts ON pwts.timesheetWeekId = pdts.timesheetWeekEntity.timesheetWeekId "
			        )

 
	
	public List<TimesheetWeekDayDetailDto> getimesheetWeekDayDetailDto();
	
	

	@Query("SELECT p FROM TimesheetWeekSummaryViewEntity p " +
	           "WHERE p.approvedBy = :approvedBy and p.accountId=:accountId " +
	           "AND p.accountProjectId = :accountProjectId " +
	           "AND p.weekNumber = :weekNumber")
	    List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(
	        @Param("approvedBy") Integer approvedBy,
	        @Param("accountId") Integer accountId,
	        @Param("accountProjectId") Integer accountProjectId,
	        @Param("weekNumber") Integer weekNumber
	        
	    );
	
	@Query("SELECT DISTINCT NEW com.feuji.timesheetentryservice.dto.AccountNameDto(a.accountId, a.accountName) FROM TimesheetWeekEntity pwt JOIN AccountEntity a ON pwt.accountId = a.accountId WHERE pwt.approvedBy = :approvedBy ")
	public List<AccountNameDto> getAccounts(@Param("approvedBy") Integer approvedBy);
	
	@Query("SELECT SUM(totalBillingHours + totalNonBillingHours + totalLeaveHours) " +
		       "FROM TimesheetWeekSummaryViewEntity " +
		       "WHERE employeeId = :employeeId AND accountProjectId = :accountProjectId and weekNumber=:weekNumber")
		public Integer getTotalHours(@Param("employeeId") Integer employeeId, 
		                             @Param("accountProjectId") Integer accountProjectId,
		                             @Param("weekNumber") Integer weekNumber);


		


}
