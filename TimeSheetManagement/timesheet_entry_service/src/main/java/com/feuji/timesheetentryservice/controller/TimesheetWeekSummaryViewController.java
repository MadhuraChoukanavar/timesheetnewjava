package com.feuji.timesheetentryservice.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.dto.AccountNameDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekSummaryViewEntity;
import com.feuji.timesheetentryservice.repository.TimesheetWeekSummaryRepo;
import com.feuji.timesheetentryservice.service.TimesheetWeekSummaryService;

@RestController

@RequestMapping("/TimesheetWeekSummaryView")
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetWeekSummaryViewController {
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekSummaryViewController.class);
	
	@Autowired
	private TimesheetWeekSummaryRepo timesheetWeekSummaryRepo;
	
	@Autowired
	private TimesheetWeekSummaryService timesheetWeekSummaryService;
	

		
	@GetMapping("/timesheets/manager/{approvedBy}/{accountId}/{accountProjectId}/{weekNumber}")

	    public ResponseEntity<List<TimesheetWeekSummaryViewEntity>> getTimesheetsForManager(
	            @PathVariable Integer approvedBy,
	            @PathVariable Integer accountId,
	            @PathVariable Integer accountProjectId,
	            @PathVariable Integer weekNumber) {
	        List<TimesheetWeekSummaryViewEntity> timesheets = timesheetWeekSummaryService.getTimesheetsForManager(approvedBy,accountId, accountProjectId, weekNumber);

	        if (timesheets.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        } else {
	            return ResponseEntity.status(HttpStatus.OK).body(timesheets);
	        }

	    }
	
	 @GetMapping("/projects/{accountId}")
	    public ResponseEntity<List<ProjectNameDto>> getAccountProjects(@PathVariable Integer accountId) {
	        log.info("Fetching account projects for accountId: {}", accountId);
	        List<ProjectNameDto> projects = timesheetWeekSummaryService.getAccountProjects(accountId);
	        if (projects != null && !projects.isEmpty()) {
	            return new ResponseEntity<>(projects, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	    }
	 
	 @GetMapping("/accounts/{approvedBy}")
	 public ResponseEntity<List<AccountNameDto>> getAccounts(@PathVariable String approvedBy){
		 log.info("Fetching account for approvedBy: {}", approvedBy);
	        List<AccountNameDto> accounts = timesheetWeekSummaryService.getAccounts(approvedBy);
	        if (accounts != null && !accounts.isEmpty()) {
	            return new ResponseEntity<>(accounts, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	 }
	 @GetMapping("/total/{employeeId}/{accountProjectId}/{weekNumber}")
	 public ResponseEntity<Integer> getTotalHours(@PathVariable Integer employeeId, @PathVariable Integer accountProjectId,@PathVariable Integer weekNumber) {
	      Integer totalHours = timesheetWeekSummaryService.getTotalHours(employeeId, accountProjectId,weekNumber);
	      return new ResponseEntity<>(totalHours, HttpStatus.OK);
	 }
	 

	
	
	

}
