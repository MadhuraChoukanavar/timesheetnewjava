package com.feuji.timesheetentryservice.serviceimpl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;
import com.feuji.timesheetentryservice.repository.TimesheetWeekSummaryRepo;
import com.feuji.timesheetentryservice.service.TimesheetWeekSummaryService;
@Service
public class TimesheetWeekSummaryViewServiceImpl implements TimesheetWeekSummaryService{
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekSummaryViewServiceImpl.class);
	@Autowired
	private TimesheetWeekSummaryRepo timesheetWeekSummaryRepo;
	
	 @Override
	    public List<TimesheetWeekSummaryViewEntity> getTimesheetsForManager(Integer approvedBy,Integer accountId, Integer accountProjectId, Integer weekNumber) {
	        List<TimesheetWeekSummaryViewEntity> timesheets = timesheetWeekSummaryRepo.getTimesheetsForManager(approvedBy, accountId,accountProjectId, weekNumber);
	        log.info("Retrieved {} timesheets for manager {} for week {} on account/project {}", timesheets.size(), approvedBy,accountId, weekNumber, accountProjectId);
	        
	        return timesheets;
	    }

	 @Override
	 public List<ProjectNameDto> getAccountProjects(Integer accountId) {
	     try {
	         log.info("Fetching account projects for accountId: {}", accountId);
	         List<ProjectNameDto> accountProjects = timesheetWeekSummaryRepo.getAccountProjects(accountId);
	         return accountProjects;
	     } catch (Exception e) {
	         log.error("Error occurred while fetching account projects: {}", e.getMessage());
	         return null;
	     }
	 }
	 @Override
	 public List<AccountNameDto> getAccounts(String approvedBy) {
	     try {
	         log.info("Fetching accounts for approvedBy: {}", approvedBy);
	         List<AccountNameDto> accounts = timesheetWeekSummaryRepo.getAccounts(approvedBy);
	         return accounts != null ? accounts : Collections.emptyList(); // Return empty list if accounts is null
	     } catch (Exception e) {
	         log.error("Error occurred while fetching accounts: {}", e.getMessage());
	         return Collections.emptyList(); // Return empty list in case of exception
	     }
	 }

	 public Integer getTotalHours(Integer employeeId, Integer accountProjectId,Integer weekNumber) {
	     try {
	         log.info("Fetching total hours for employeeId {} and accountProjectId {};", employeeId, accountProjectId);
	         Integer totalHours = timesheetWeekSummaryRepo.getTotalHours(employeeId, accountProjectId,weekNumber);
	         return totalHours;
	     } catch(Exception e) {
	         log.error("Error occurred while fetching total hours: {}", e.getMessage());
	         return null;
	     }    
	}	


	
	
	

}
