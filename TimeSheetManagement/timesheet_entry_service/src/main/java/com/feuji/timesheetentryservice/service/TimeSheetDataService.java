package com.feuji.timesheetentryservice.service;

import java.util.List;

import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;

public interface TimeSheetDataService {

	// public void saveTimeSheetHome(TimesheetHomeUiBean
	// timesheetHomeUiBean,TimesheetHomeUiDayBean timesheetHomeUiDayBean);

	public List<TimesheetWeekEntity> saveAll(List<WeekAndDayDataBean> yourJavaClassList);

	public List<WeekAndDayDto> fetchAllWeekDayRecordsById(Integer employeeId, Integer accountId, String weekStartDate,
			String weekEndDate);
	// public List<TimesheetWeekDayDetailDto> fetchAllWeekDayRecordsById();

//	/void deleteRecords(Integer timesheetWeekId);

	// TimesheetDayEntity deleteDayRecord(Integer timesheetWeekId,Integer
	// timesheetDayId);

	List<TimesheetDayEntity> deleteDayRecord(WeekAndDayDto weekAndDayDto);

	List<TimesheetWeekEntity> submittingTimesheet(String weekStartDate, Integer timesheetStatus);

	// void deleteRecords(Integer timesheetWeekId, Integer employeeId, String
	// weekStartDate);
}
