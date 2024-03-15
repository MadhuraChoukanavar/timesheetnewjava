package com.feuji.timesheetentryservice.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;
import com.feuji.timesheetentryservice.dto.SaveAndEditRecordsDto;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.service.TimeSheetDataService;
import com.feuji.timesheetentryservice.service.TimesheetWeekService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/timesheetdata")
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetDataController {
	@Autowired
	TimeSheetDataService timeSheetDataService;

	@Autowired
	TimesheetWeekService timesheetWeekService;
	
	
	


	/**
	 * Handles the HTTP POST request to save a list of timesheet data for multiple
	 * weeks.
	 *
	 * @param timesheetData A list of WeekAndDayDataBean objects containing the
	 *                      timesheet data for each week.
	 * @return ResponseEntity containing the saved TimesheetWeekEntity objects and
	 *         HTTP status code.
	 */
	
	@PostMapping("/saveedit/{weekStartDate}")
	public String saveupdate(
			@RequestBody SaveAndEditRecordsDto weekAndDayDataBeans ,@PathVariable String weekStartDate) {
		

		timeSheetDataService.saveOrUpdate(weekAndDayDataBeans, weekStartDate);
		return "came to controller ";
				
	}

//	@PostMapping("/saveall")
//
//	public ResponseEntity<List<TimesheetWeekEntity>> saveTimesheetDataAll(
//			@RequestBody List<WeekAndDayDataBean> timesheetData) {
//
//		try {
//
//			List<TimesheetWeekEntity> saveAll = timeSheetDataService.saveAll(timesheetData);
//
//			System.out.println(saveAll);
//
//			return new ResponseEntity<>(saveAll, HttpStatus.CREATED);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	/**
	 * Handles the HTTP GET request to retrieve the timesheet data for a specific
	 * week and employee.
	 *
	 * @param accountId     The ID of the account associated with the timesheet
	 *                      data.
	 * @param employeeId    The ID of the employee for whom the timesheet data is
	 *                      being retrieved.
	 * @param weekStartDate The start date of the week for which timesheet data is
	 *                      requested (in the format "YYYY-MM-DD").
	 * @param weekEndDate   The end date of the week for which timesheet data is
	 *                      requested (in the format "YYYY-MM-DD").
	 * @return ResponseEntity containing a list of WeekAndDayDto objects
	 *         representing the timesheet data and HTTP status code.
	 */
	@GetMapping("/getallweekdayData/{accountId}/{employeeId}/{weekStartDate}/{weekEndDate}")
	public ResponseEntity<List<WeekAndDayDto>> getAttWeekDayData(@PathVariable Integer accountId,
			@PathVariable Integer employeeId, @PathVariable String weekStartDate, @PathVariable String weekEndDate) {

		try {

			List<WeekAndDayDto> fetchAllWeekDayRecordsById = timeSheetDataService.fetchAllWeekDayRecordsById(accountId,
					employeeId, weekStartDate, weekEndDate);

			return new ResponseEntity<>(fetchAllWeekDayRecordsById, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the HTTP POST request to delete a timesheet day record.
	 *
	 * @param weekAndDayDto A WeekAndDayDto object containing the data for the
	 *                      timesheet day record to be deleted.
	 * @return ResponseEntity containing a list of TimesheetDayEntity objects
	 *         representing the deleted record and HTTP status code.
	 */
	@PostMapping("/delete")
	public ResponseEntity<List<TimesheetDayEntity>> deleteTheRecord(@RequestBody WeekAndDayDto weekAndDayDto) {
		try {
			System.out.println("hi delete");
			List<TimesheetDayEntity> deleteDayRecord = timeSheetDataService.deleteDayRecord(weekAndDayDto);

			if (deleteDayRecord == null || deleteDayRecord.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(deleteDayRecord, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/editData")
	public void updateData(@RequestBody WeekAndDayDto weekAndDayDto) {

	}

	/**
	 * Handles the HTTP POST request to submit a timesheet for a specific week.
	 *
	 * @param weekStartDate   The start date of the week for which the timesheet is
	 *                        being submitted (in the format "YYYY-MM-DD").
	 * @param timesheetStatus The status of the submitted timesheet.
	 * @return ResponseEntity containing a list of TimesheetWeekEntity objects
	 *         representing the submitted timesheet and HTTP status code.
	 */
	@PostMapping("submitAction")
	public ResponseEntity<List<TimesheetWeekEntity>> submitTimesheet(@RequestParam String weekStartDate,
			@RequestParam Integer timesheetStatus) {
		try {

			log.info("Submitting timesheet for week starting on {} with status: {}", weekStartDate, timesheetStatus);

			List<TimesheetWeekEntity> submittingTimesheet = timeSheetDataService.submittingTimesheet(weekStartDate,
					timesheetStatus);

			if (submittingTimesheet != null && !submittingTimesheet.isEmpty()) {

				return new ResponseEntity<>(submittingTimesheet, HttpStatus.OK);
			} else {

				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
