package com.feuji.timesheetentryservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.timesheetentryservice.entity.HolidayEntity;
import com.feuji.timesheetentryservice.exception.HolidayDateExistsException;
import com.feuji.timesheetentryservice.exception.HolidayNameAndHolidayDateExistException;
import com.feuji.timesheetentryservice.exception.HolidayNameExistException;
import com.feuji.timesheetentryservice.exception.HolidayNotFoundException;
import com.feuji.timesheetentryservice.service.HolidayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/holiday")
@CrossOrigin(origins = "http://localhost:4200")
public class HolidayController {
	private static Logger log = LoggerFactory.getLogger(HolidayController.class);
	@Autowired
	private HolidayService holidayService;

	@PostMapping(path="/save")
	public ResponseEntity<HolidayEntity> save(@RequestBody HolidayEntity holidayEntity) {
		try {
		log.info("Saving holiday details {}", holidayEntity);
		holidayService.save(holidayEntity);

		ResponseEntity<HolidayEntity> responseEntity = new ResponseEntity<>(holidayEntity, HttpStatus.CREATED);
		return responseEntity;
		}
		catch (HolidayNameAndHolidayDateExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch (HolidayDateExistsException|HolidayNameExistException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(path = "/{holidayId}")
	public ResponseEntity<HolidayEntity> get(@PathVariable Integer holidayId) {
		log.info("Fetching department_details {}", holidayId);

		HolidayEntity holidayEntity = holidayService.get(holidayId);

		ResponseEntity<HolidayEntity> responseEntity = new ResponseEntity<>(holidayEntity, HttpStatus.OK);
		return responseEntity;
	}

	@PutMapping(path="/update")
	public ResponseEntity<String> handlePutRequestupdate(@RequestBody HolidayEntity holidayEntity) {

		log.info("start the holiday_details Controller:update");
		try {
			holidayService.update(holidayEntity);
			log.info("Update holiday_details {}", holidayEntity);
			return ResponseEntity.status(HttpStatus.OK).body("updated");
		} catch (HolidayNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@DeleteMapping("/{holidayId}")
	public ResponseEntity<HolidayEntity> delate(@PathVariable Integer holidayId) {
		// log.info("holiday delete",holidayId);)
		HolidayEntity holidayEntity = holidayService.delete(holidayId);
		log.info("Delete department_details {}", holidayId);
		ResponseEntity<HolidayEntity> responseEntity = new ResponseEntity<HolidayEntity>(holidayEntity, HttpStatus.OK);
		return responseEntity;

	}

	@GetMapping("/getWeekHolidaysDayIds/{startweekofDate}")
	public List<Integer> getWeekHolidaysDayIds(@PathVariable String startweekofDate) {
		// log.info("holiday delete",holidayId);)
		System.out.println(startweekofDate);
		List<Integer> holidayList = holidayService.getWeekHolidaysDayIds(startweekofDate);

		return holidayList;

	}
	@GetMapping("/getHolidayByYear/{year}")
	public List<HolidayEntity> getHolidayByYear(@PathVariable int year) {
		// log.info("holiday delete",holidayId);)
		System.out.println(year);
		List<HolidayEntity> holidayList = holidayService.getHolidayByYear(year);

		return holidayList;

	}

}