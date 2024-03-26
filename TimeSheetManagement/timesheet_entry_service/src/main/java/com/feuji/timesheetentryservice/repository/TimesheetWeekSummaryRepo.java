package com.feuji.timesheetentryservice.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;

import jakarta.transaction.Transactional;


//import com.feuji.timesheetentryservice.entity.ProjectTimesheetSummaryView;
@EnableJpaRepositories
@Transactional
public interface TimesheetWeekSummaryRepo extends JpaRepository<TimesheetWeekSummaryViewEntity,Integer>{
//	
//	@Query("SELECT p FROM TimesheetWeekSummaryViewEntity p " +
//	           "WHERE p.approvedBy = :approvedBy " +
//	           "AND p.accountProjectId = :accountProjectId " +
//	           "AND p.weekNumber = :weekNumber")
// List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager( @Param("approvedBy") Integer approvedBy,
//		 @Param("accountProjectId") Integer accountProjectId,
//	        @Param("weekNumber") Integer weekNumber
//	    );
	
 
	@Query("SELECT distinct NEW com.feuji.timesheetentryservice.dto.ProjectNameDto(pwt.accountId,ap.accountProjectId,ap.projectName) FROM TimesheetWeekEntity pwt JOIN AccountProjectsEntity ap ON pwt.accountProjectId = ap.accountProjectId WHERE pwt.accountId = :accountId And pwt.employeeId=:employeeId")
	public List<ProjectNameDto> getAccountProjects(@Param("accountId") Integer accountId,@Param("employeeId") Integer employeeId);

	
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
	           "WHERE p.approvedBy = :approvedBy and p.accountId=:accountId "  +
	           "AND p.weekNumber = :weekNumber")
	    List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(
	        @Param("approvedBy") Integer approvedBy,
	        @Param("accountId") Integer accountId,
	      
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

	

		
//	@Modifying
//	@Query(value="update project_week_timesheet set timesheet_status=60 where employee_id=:employeeId and account_project_id=:accountProjectId and week_number=:weekNumber",nativeQuery=true)
//	public void rejectedTimesheet(Integer employeeId,Integer accountProjectId,Integer weekNumber);
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto(" +
	        "   pwt.weekStartDate, " +
	        "   ep.email, " +
	        "   ap.plannedStartDate, " +
	        "   ap.plannedEndDate, " +
	        "   pwt.weekEndDate, " +
	        "   ap.projectName, " +
	        "   acc.accountName, " +
	        "   ep.employeeCode, "+
	        "   ep.designation, "+
	        "   ap.projectManagerId, "+
	        "   crdStatus.referenceDetailValue AS timesheetStatus, "+
	        "   CONCAT(ep.firstName,' ', ep.lastName) AS fullName,"+
	        "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END) AS billingHours, "+
	        "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END) AS nonBillingHours, "+
	        "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8 AS leaveHours) "+
	        "FROM TimesheetWeekEntity pwt "+
	        "JOIN EmployeeEntity ep ON ep.employeeId=pwt.employeeId "+
	        "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "+
	        "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "+
	        "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "+
	        "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "+
	        "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "+
	        "WHERE YEAR(pdt.date) = :year " +
	       
	        "AND acc.accountId = :accountId " +
	        "AND ap.projectManagerId = :projectManagerId "+
	        "GROUP BY ep.email, ap.plannedStartDate, ap.plannedEndDate, pwt.uuid, ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue")
	List<TimeSheeApprovalDto> getTimeSheetApproval(@Param("projectManagerId") Integer projectManagerId,
			@Param("year") Integer year,
            @Param("accountId") Integer accountId);
	                                                

	
	
	
	
	@Query("SELECT new com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto(" +
	        "   pwt.weekStartDate, " +
	        "   ep.email, " +
	        "   ap.plannedStartDate, " +
	        "   ap.plannedEndDate, " +
	        "   pwt.weekEndDate, " +
	        "   ap.projectName, " +
	        "   acc.accountName, " +
	        "   ep.employeeCode, "+
	        "   ep.designation, "+
	        "   ap.projectManagerId, "+
	        "   crdStatus.referenceDetailValue, "+
	        "   CONCAT(ep.firstName,' ', ep.lastName), "+
	        "   SUM(CASE WHEN crd.referenceDetailValue = 'Billable' THEN pdt.numberOfHours ELSE 0 END), "+
	        "   SUM(CASE WHEN crd.referenceDetailValue = 'Non billable' THEN pdt.numberOfHours ELSE 0 END), "+
	        "   SUM(CASE WHEN crd.referenceDetailValue = 'Leave' THEN pdt.numberOfHours ELSE 0 END)/8) "+
	        "FROM TimesheetWeekEntity pwt "+
	        "JOIN EmployeeEntity ep ON ep.employeeId = pwt.employeeId "+
	        "JOIN AccountProjectsEntity ap ON ap.accountProjectId = pwt.accountProjectId "+
	        "JOIN AccountEntity acc ON acc.accountId = pwt.accountId "+
	        "JOIN TimesheetDayEntity pdt ON pdt.timesheetWeekEntity.timesheetWeekId = pwt.timesheetWeekId "+
	        "JOIN CommonReferenceDetailsEntity crd ON pdt.attendanceType = crd.referenceDetailId "+
	        "JOIN CommonReferenceDetailsEntity crdStatus ON crdStatus.referenceDetailId = pwt.timesheetStatus "+
	        "WHERE YEAR(pdt.date) = :year " +
	        "AND MONTHNAME(pdt.date) = :month " +
	        "AND acc.accountId = :accountId " +
	        "AND ep.employeeId = :employeeId " +
	        "AND ap.projectManagerId = :projectManagerId "+
	        "GROUP BY ep.email,ap.plannedStartDate,ap.plannedEndDate,pwt.uuid, ap.projectManagerId, ep.designation, ep.employeeCode, pwt.weekStartDate, ep.lastName, ep.firstName, pwt.weekEndDate, ap.projectName, acc.accountName, crdStatus.referenceDetailValue")
	List<TimeSheeApprovalDto> getTimeSheetApprovalByEmployeeId(@Param("projectManagerId") int projectManagerId,
	                                                  @Param("month") String month,
	                                                  @Param("year") Integer year,
	                                                  @Param("accountId") Integer accountId,
	                                                  @Param("employeeId") Integer employeeId);
	
	
	
	



}