package com.feuji.timesheetentryservice.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;



public interface TimesheetWeekSummaryService {
	
	  List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(
		       Integer approvedBy,
		       Integer accountId,
		      
		     Integer weekNumber
		    );
	  
	  public List<ProjectNameDto> getAccountProjects( Integer accountId,Integer employeeId);
	  
	  List<AccountNameDto> getAccounts(Integer approvedBy);
	  
	  public Integer getTotalHours( Integer employeeId, Integer accountProjectId,Integer weekNumber);


	  List<TimeSheeApprovalDto> getTimeSheetApproval(Integer projectManagerId, Integer year,  Integer accountId );
	  List<TimeSheeApprovalDto> getTimeSheetApprovalByEmployeeId(Integer projectManagerId,String month, Integer year,  Integer accountId ,Integer employeeId);

//	String rejectedTimesheet(Integer employeeId, Integer accountProjectId, Integer weekNumber);

	  

}