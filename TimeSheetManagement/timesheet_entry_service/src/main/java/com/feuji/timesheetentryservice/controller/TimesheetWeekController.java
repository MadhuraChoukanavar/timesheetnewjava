package com.feuji.timesheetentryservice.controller;

import java.util.List;

import java.util.Map;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.bean.TimesheetWeekBean;
import com.feuji.timesheetentryservice.dto.AccountProjectResourceMappingDto;
import com.feuji.timesheetentryservice.dto.ProjectNameDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskDto;
import com.feuji.timesheetentryservice.dto.ProjectTaskTypeNameDto;
import com.feuji.timesheetentryservice.dto.TimeSheetHistoryDto;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.repository.TimesheetWeekRepo;
import com.feuji.timesheetentryservice.service.TimesheetWeekService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/timesheet")
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetWeekController {
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekController.class);

	@Autowired
	TimesheetWeekService timesheetWeekService;
	@Autowired
	TimesheetWeekRepo timesheetWeekRepo;

	@PostMapping("/save")
	public ResponseEntity<TimesheetWeekEntity> saveTimesheetWeek(@RequestBody TimesheetWeekBean timesheetWeekBean) {

		try {
			log.info("timesheet week controller", timesheetWeekBean);
			TimesheetWeekEntity timesheetWeekEntity = timesheetWeekService.save(timesheetWeekBean);
			return new ResponseEntity<>(timesheetWeekEntity, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/gettimesheetweek/{id}")
	public ResponseEntity<TimesheetWeekEntity> getTimesheetById(@PathVariable Integer id) {
		try {
			log.info("getting timesheet", id);
			TimesheetWeekEntity timesheetWeekEntity = timesheetWeekService.getById(id);
			return new ResponseEntity<>(timesheetWeekEntity, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getproject")
	public ResponseEntity<List<ProjectNameDto>> getProjectBYEMpId(@RequestParam Integer employeeId,
			@RequestParam Integer accountId) {
		log.info("passing employee id", employeeId);
		List<ProjectNameDto> projectNameByEmpId = timesheetWeekService.getProjectNameByEmpId(employeeId, accountId);
		return new ResponseEntity<>(projectNameByEmpId, HttpStatus.CREATED);
	}

	@GetMapping("/getprojecttasktype")
	public ResponseEntity<List<ProjectTaskTypeNameDto>> getProjectTaskType(@RequestParam Integer employeeId,
			@RequestParam Integer accountProjectId) {
		log.info("passing employee id", employeeId);
		List<ProjectTaskTypeNameDto> projectTaskType = timesheetWeekService.getProjectTaskTypeName(employeeId,
				accountProjectId);
		return new ResponseEntity<>(projectTaskType, HttpStatus.CREATED);
	}

	@GetMapping("/getprojecttask")
	public ResponseEntity<List<ProjectTaskDto>> getProjectTask(@RequestParam Integer taskTypeId) {
		log.info("passing employee id", taskTypeId);
		List<ProjectTaskDto> projectTask = timesheetWeekService.getProjectTask(taskTypeId);
		return new ResponseEntity<>(projectTask, HttpStatus.CREATED);
	}

	/**
	 * Handles the HTTP GET request to retrieve account details based on the user's
	 * employee ID.
	 *
	 * @param userEmpId The employee ID of the user for whom account details are
	 *                  being retrieved.
	 * @return ResponseEntity containing a list of AccountProjectResourceMappingDto
	 *         objects representing account details and HTTP status code.
	 */
	@GetMapping(path = "/getaccountdetails")
	public ResponseEntity<List<AccountProjectResourceMappingDto>> findAccountNameByUserEmpId(
			@RequestParam Integer userEmpId) {
		try {

			List<AccountProjectResourceMappingDto> accountProjectResourceMappingDtos = timesheetWeekService
					.findAccountNameByUserEmpId(userEmpId);

			log.info("Fetching accountProjectResourceMappingDtos for userEmpId {}: {}", userEmpId,
					accountProjectResourceMappingDtos);

			return ResponseEntity.status(HttpStatus.OK).body(accountProjectResourceMappingDtos);
		} catch (Exception e) {

			log.error("Error fetching account details for userEmpId {}: {}", userEmpId, e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve time sheet history for a specific
	 * month, year, account, and employee.
	 *
	 * @param month       The month for which time sheet history is being retrieved
	 *                    (e.g., "January", "February").
	 * @param year        The year for which time sheet history is being retrieved.
	 * @param accountName The name of the account for which time sheet history is
	 *                    being retrieved.
	 * @param employeeId  The ID of the employee for whom time sheet history is
	 *                    being retrieved.
	 * @return ResponseEntity containing a list of TimeSheetHistoryDto objects
	 *         representing time sheet history and HTTP status code.
	 */
	@GetMapping(path = "/gettimeSheetHistory/{month}/{year}/{accountName}/{employeeId}")
	public ResponseEntity<List<TimeSheetHistoryDto>> timeSheetHistoryDto(@PathVariable String month,
			@PathVariable int year, @PathVariable String accountName, @PathVariable int employeeId) {
		try {

			List<TimeSheetHistoryDto> timeSheetHistory = timesheetWeekService.timeSheetHistoryDto(month, year,
					accountName, employeeId);

			log.info("Fetching timeSheetHistory for {} {} accountName: {} employeeId: {}", month, year, accountName,
					employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for {} {} accountName: {} employeeId: {}: {}", month, year,
					accountName, employeeId, e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve time sheet history for a specific
	 * year, account, and employee.
	 *
	 * @param year        The year for which time sheet history is being retrieved.
	 * @param accountName The name of the account for which time sheet history is
	 *                    being retrieved.
	 * @param employeeId  The ID of the employee for whom time sheet history is
	 *                    being retrieved.
	 * @return ResponseEntity containing a list of TimeSheetHistoryDto objects
	 *         representing time sheet history and HTTP status code.
	 */

	@GetMapping(path = "/gettimeSheetHistory/{year}/{accountName}/{employeeId}")
	public ResponseEntity<List<TimeSheetHistoryDto>> getTimeSheetHistoryByYear(@PathVariable int year,
			@PathVariable String accountName, @PathVariable int employeeId) {
		try {
			List<TimeSheetHistoryDto> timeSheetHistory = timesheetWeekService.getTimeSheetHistoryByYear(year,
					accountName, employeeId);

			log.info("Fetching timeSheetHistory for year: {} accountName: {} employeeId: {}", year, accountName,
					employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for year: {} accountName: {} employeeId: {}: {}", year,
					accountName, employeeId, e.getMessage()); // Example: Logging the error

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve time sheet history for a specific
	 * month, year, and employee, grouped by account.
	 *
	 * @param month      The month for which time sheet history is being retrieved
	 *                   (e.g., "January", "February").
	 * @param year       The year for which time sheet history is being retrieved.
	 * @param employeeId The ID of the employee for whom time sheet history is being
	 *                   retrieved.
	 * @return ResponseEntity containing a list of TimeSheetHistoryDto objects
	 *         representing time sheet history grouped by account and HTTP status
	 *         code.
	 */
	@GetMapping(path = "/gettimeSheetHistory/getAccountByMonthAndYear/{month}/{year}/{employeeId}")
	public ResponseEntity<List<TimeSheetHistoryDto>> getAccountByMonthAndYear(@PathVariable String month,
			@PathVariable int year, @PathVariable int employeeId) {
		try {

			List<TimeSheetHistoryDto> timeSheetHistory = timesheetWeekService.getAccountByMonthAndYear(month, year,
					employeeId);

			log.info("Fetching timeSheetHistory for {} {} employeeId: {}", month, year, employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(timeSheetHistory);
		} catch (Exception e) {

			log.error("Error fetching time sheet history for {} {} employeeId: {}: {}", month, year, employeeId,
					e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP GET request to retrieve the years for which time sheet
	 * history is available for a specific employee.
	 *
	 * @param employeeId The ID of the employee for whom years are being retrieved.
	 * @return ResponseEntity containing a list of years for which time sheet
	 *         history is available and HTTP status code.
	 */
	@GetMapping(path = "/gettimeSheetHistory/getYears/{employeeId}")
	public ResponseEntity<List<Integer>> getYear(@PathVariable int employeeId) {
		try {

			List<Integer> years = timesheetWeekService.getYear(employeeId);

			log.info("Fetching Years for employeeId: {}", employeeId);

			return ResponseEntity.status(HttpStatus.OK).body(years);
		} catch (Exception e) {

			log.error("Error fetching years for employeeId {}: {}", employeeId, e.getMessage());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
