package com.feuji.timesheetentryservice.serviceimpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.feuji.timesheetentryservice.bean.TimesheetWeekBean;
import com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheeApprovalDto;
import com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.exception.WeekNotFoundException;
import com.feuji.timesheetentryservice.repository.TimesheetWeekRepo;
import com.feuji.timesheetentryservice.service.TimesheetWeekService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TimesheetWeekServiceImpl implements TimesheetWeekService {
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekServiceImpl.class);

	@Autowired
	TimesheetWeekRepo timesheetWeekRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public TimesheetWeekEntity save(TimesheetWeekBean timesheetWeekBean) {

		TimesheetWeekEntity timesheetWeekEntity = modelMapper.map(timesheetWeekBean, TimesheetWeekEntity.class);
		log.info("saving timesheet entity " + timesheetWeekEntity);
		timesheetWeekEntity = timesheetWeekRepo.save(timesheetWeekEntity);
		return timesheetWeekEntity;

	}

	@Override
	public TimesheetWeekEntity getById(Integer id) {
		try {
			Optional<TimesheetWeekEntity> optionalWeekTimesheet = timesheetWeekRepo.findById(id);
			if (optionalWeekTimesheet.isPresent()) {
				return optionalWeekTimesheet.get();

			} else {
				throw new WeekNotFoundException("week with id not found");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error("Week not found: {}", e.getMessage());
			return null;
		}
	}
	

	@Override
	public List<ProjectNameDto> getProjectNameByEmpId(Integer employeeId ,Integer accountId) {

		try {
			log.info("projects with emp id :", employeeId);
			List<ProjectNameDto> projectsOfEmployee = timesheetWeekRepo.getProjectsByEmpId(employeeId,accountId);
			return projectsOfEmployee;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public List<ProjectTaskTypeNameDto> getProjectTaskTypeName(Integer employeeId, Integer accountProjectId) {
		try {
			log.info("projects with emp id :", employeeId);
			List<ProjectTaskTypeNameDto> projectsTaskType = timesheetWeekRepo.getProjectTaskTypeName(employeeId,accountProjectId);
			return projectsTaskType;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<ProjectTaskDto> getProjectTask(Integer taskTypeId) {
		try
		{
			log.info("tasktype id :" ,taskTypeId);
			List<ProjectTaskDto> projectTask = timesheetWeekRepo.getProjectTask(taskTypeId);
		return projectTask;
		
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<AccountProjectResourceMappingDto> findAccountNameByUserEmpId(Integer userEmpId) {
		   
		log.info("Employee ID   : "+ userEmpId );
		
		List<AccountProjectResourceMappingDto> accountProjectResourceMappingDtos=timesheetWeekRepo.findAccountNameByUserEmpId(userEmpId);
		return accountProjectResourceMappingDtos;
	}
	
	@Override
	public List<TimeSheetHistoryDto> timeSheetHistoryDto(String month, int year,String accountName,int employeeId) {
		
		try
		{
		System.out.println(month+" "+year+" "+accountName);
		List<TimeSheetHistoryDto>   timeSheetHistory =timesheetWeekRepo.getTimeSheetHistory(month, year,accountName, employeeId);

		log.info("timeSheetHistory :" ,timeSheetHistory);
		return timeSheetHistory;
		}
		catch (Exception e) {
//			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}
		return null;
	}

	@Override
	public List<TimeSheetHistoryDto> getTimeSheetHistoryByYear(int year, String accountName, int employeeId) {
		try
		{
		System.out.println(employeeId+" "+year+" "+accountName);
		List<TimeSheetHistoryDto>   timeSheetHistory =timesheetWeekRepo.getTimeSheetHistoryByYear(year, accountName, employeeId);

		log.info("timeSheetHistory :" ,timeSheetHistory);
		return timeSheetHistory;
		}
		catch (Exception e) {
//			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}
		return null;
	}

	@Override
	public List<TimeSheetHistoryDto> getAccountByMonthAndYear(String month, int year, int employeeId) {
		try
		{
		System.out.println(employeeId+" "+year);
		List<TimeSheetHistoryDto>   timeSheetHistory =timesheetWeekRepo.getAccountByMonthAndYear(month, year, employeeId);

		log.info("timeSheetHistory :" ,timeSheetHistory);
		return timeSheetHistory;
		}
		catch (Exception e) {
//			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Integer> getYear(int employeeId) {
		try
		{
		
		List<Integer>   years =timesheetWeekRepo.getYear( employeeId);

		log.info("Years :" ,years);
		return years;
		}
		catch (Exception e) {
//			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}
		return null;
	}
	@Override
	public String updateTimesheetStatus(Integer employeeId,Integer accountId,String weekStartDate) {
		Date startDate = convertDateStringToDate(weekStartDate);
		try {
			timesheetWeekRepo.updateTimesheetStatus(employeeId,accountId,startDate);
			return "Update timesheetStatus successfully";
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}


	@Override
	public String rejectedTimesheet(Integer employeeId, Integer accountId,String weekStartDate) {
		Date startDate = convertDateStringToDate(weekStartDate);
		try {
			timesheetWeekRepo.rejectedTimesheet(employeeId,accountId,startDate);
			return " Rejected Timesheet successfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "getting exception";
		}
		
		
	}
	
	public static Date convertDateStringToDate(String dateString) {
		try {

			LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

			Date date = java.sql.Date.valueOf(localDate);
			return date;

		} catch (DateTimeParseException e) {

			System.out.println("Error parsing the date: " + e.getMessage());
			return null;
		}

	}
	@Override
	public List<TimeSheeApprovalDto> timeSheetHistoryDto(String month, int year,Integer accountId) {
		
		try
		{
		System.out.println(month+" "+year+" "+accountId);
		List<TimeSheeApprovalDto>   timeSheeApproval =timesheetWeekRepo.getTimeSheetHistory(month, year,accountId);

		log.info("timeSheetHistory :" ,timeSheeApproval);
		return timeSheeApproval;
		}
		catch (Exception e) {
//			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}
		return null;
	}
}