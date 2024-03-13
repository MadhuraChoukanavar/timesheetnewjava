package com.feuji.timesheetentryservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.bean.TimesheetDayBean;
import com.feuji.timesheetentryservice.bean.TimesheetWeekBean;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.repository.TimesheetDayRepo;
import com.feuji.timesheetentryservice.service.TimesheetDayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/timesheetday")
public class TimesheetDayController {
	private static Logger log = LoggerFactory.getLogger(TimesheetWeekController.class);

	@Autowired
	TimesheetDayService timesheetDayService;
	@Autowired
	TimesheetDayRepo timesheetDayRepo;
	
	/**
	 * Handles the HTTP POST request to save a timesheet day record.
	 *
	 * @param timesheetDayBean A TimesheetDayBean object containing the data for the timesheet day record to be saved.
	 * @return ResponseEntity containing the saved TimesheetDayEntity object and HTTP status code.
	 */
	
	@PostMapping("/save")
	public ResponseEntity<TimesheetDayEntity> saveTimesheetWeek(@RequestBody TimesheetDayBean timesheetDayBean) {
		try {
			log.info("timesheet week controller", timesheetDayBean);
			TimesheetDayEntity timesheetDayEntity = timesheetDayService.saveTimesheetDay(timesheetDayBean);
			return new ResponseEntity<>(timesheetDayEntity, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	/**
	 * Handles the HTTP GET request to retrieve a timesheet day record by its ID.
	 *
	 * @param id The ID of the timesheet day record to be retrieved.
	 * @return ResponseEntity containing the retrieved TimesheetDayEntity object and HTTP status code.
	 */
	
	@GetMapping("/gettimesheetday/{id}")
	public ResponseEntity<TimesheetDayEntity> getTimesheetById(@PathVariable Integer id) {
		try {
			log.info("getting timesheet day", id);
			TimesheetDayEntity timeSheetDayEntity = timesheetDayService.getTimeSheetDayByuuid(id);
			return new ResponseEntity<>(timeSheetDayEntity, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
}