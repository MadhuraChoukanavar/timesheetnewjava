package com.feuji.timesheetentryservice.serviceimpl;

import com.feuji.timesheetentryservice.service.HolidayService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.timesheetentryservice.controller.HolidayController;
import com.feuji.timesheetentryservice.entity.HolidayEntity;
import com.feuji.timesheetentryservice.exception.HolidayNotFoundException;
import com.feuji.timesheetentryservice.repository.HolidayRepository;
import com.feuji.timesheetentryservice.service.HolidayService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class HolidayServiceImpl implements HolidayService {
	private static Logger log = LoggerFactory.getLogger(HolidayController.class);
	@Autowired
	private HolidayRepository holidayRepository;

	@Override
	public void save(HolidayEntity holidayEntity) {
		holidayRepository.save(holidayEntity);
		log.info("Holiday details are save", holidayEntity);

	}
	


	@Override
	public List<HolidayEntity> getAll() {
		// Fetch all holidays from the repository and filter by is_delete = false
		return holidayRepository.findAll().stream().filter(holiday -> !holiday.isDeleted())
				.collect(Collectors.toList());
	}

	@Override
	public void update(HolidayEntity holidayEntity) throws HolidayNotFoundException {
		Optional<HolidayEntity> optional = holidayRepository.findById(holidayEntity.getHolidayId());
		if (optional.isPresent()) {
			holidayRepository.save(holidayEntity);
			System.out.println("update successfull");

		} else {
			throw new HolidayNotFoundException("HolidayNotFoundExceptionbyholidayId-" + holidayEntity.getHolidayId());
		}
	}

	@Override
	public HolidayEntity get(Integer holidayId) {

		return holidayRepository.findById(holidayId).get();
	}

	@Override
	public HolidayEntity delete (Integer holidayId) {
		log.info("service method{}",holidayId);
	HolidayEntity optional = holidayRepository.findById(holidayId).orElseThrow(()->new IllegalArgumentException("id not found"));
	  optional.setDeleted(true);
	update(optional);
	
//		if (optional.isPresent()) {
//			
//			holidayRepository.updateIsDeleted(holidayId);
//
//			System.out.println("deleted successfull");
//		} else {
//			optional.orElseThrow();
//		}

		return optional;
	}



	

}
