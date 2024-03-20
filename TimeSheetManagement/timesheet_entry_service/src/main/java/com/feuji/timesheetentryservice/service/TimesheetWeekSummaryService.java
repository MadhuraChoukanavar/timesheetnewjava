package com.feuji.timesheetentryservice.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;



public interface TimesheetWeekSummaryService {
	
	  List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(
		       Integer approvedBy,
		       Integer accountId,
		       Integer accountProjectId,
		     Integer weekNumber
		    );
	  
	  public List<ProjectNameDto> getAccountProjects( Integer accountId);
	  
	  List<AccountNameDto> getAccounts(String approvedBy);
	  
	  public Integer getTotalHours( Integer employeeId, Integer accountProjectId,Integer weekNumber);




}
