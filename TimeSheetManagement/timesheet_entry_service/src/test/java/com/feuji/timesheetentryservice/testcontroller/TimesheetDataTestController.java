package com.feuji.timesheetentryservice.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;
import com.feuji.timesheetentryservice.controller.TimesheetDataController;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.HolidayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.service.TimeSheetDataService;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
public class TimesheetDataTestController {
	@Mock
 private TimeSheetDataService timeSheetDataService;
	@InjectMocks
	private TimesheetDataController timesheetDataController;
	
//	@Test
//	public void testsaveTimesheetData() {
//	    LocalDate weekStartDate = LocalDate.parse("2024-03-05");
//	    WeekAndDayDto weekAndDayDto = WeekAndDayDto.builder()
//	                                        .timesheetWeekId(1)
//	                                        .timesheetStatus(1)
//	                                        .weekStartDate(Date.valueOf(weekStartDate))
//	                                        .build();
//	    WeekAndDayDataBean dataBean = new WeekAndDayDataBean(); // Assuming there's a constructor or builder that accepts WeekAndDayDto
//	    List<WeekAndDayDataBean> dataList = Collections.singletonList(dataBean);
//	    timeSheetDataService.saveAll(dataList);
//	    verify(timeSheetDataService, times(1)).saveAll(dataList);
//	}
	 @Test
	    public void testFetchAllWeekDayRecordsById() {
	        // Prepare test data
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        LocalDate weekEndDate = LocalDate.parse("2024-04-05");
	        List<WeekAndDayDto> expectedResultList = Collections.singletonList(
	            WeekAndDayDto.builder()
	                .timesheetWeekId(1)
	                .timesheetStatus(1)
	                .weekStartDate(Date.valueOf(weekStartDate))
	                .accountId(1)
	                .employeeId(1)
	                .build()
	        );

	        // Mock the service method to return the test data
	        when(timeSheetDataService.fetchAllWeekDayRecordsById(anyInt(), anyInt(), anyString(), anyString()))
	            .thenReturn(expectedResultList);

	        // Call the service method
	        List<WeekAndDayDto> actualResultList = timeSheetDataService.fetchAllWeekDayRecordsById(1, 1, "2024-03-05", "2024-04-05");

	        // Assertions
	        assertThat(actualResultList).isNotNull();
	        assertThat(actualResultList).hasSize(1);
	        assertThat(actualResultList.get(0).getAccountId()).isEqualTo(1);
	        // Add more assertions as needed
	    }
		
	@Test
	public void testdelteDayRecor() {
		 LocalDate weekStartDate = LocalDate.parse("2024-03-05");
		    WeekAndDayDto weekAndDayDto = WeekAndDayDto.builder()
		                                        .timesheetWeekId(1)
		                                        .timesheetStatus(1)
		                                        .weekStartDate(Date.valueOf(weekStartDate))
		                                        .build();
		    WeekAndDayDataBean dataBean = new WeekAndDayDataBean(); // Assuming there's a constructor or builder that accepts WeekAndDayDto
		    List<WeekAndDayDataBean> dataList = Collections.singletonList(dataBean);
		    timeSheetDataService.deleteDayRecord(weekAndDayDto);
		    verify(timeSheetDataService, times(1)).deleteDayRecord(weekAndDayDto);
	}
	 @Test
	    public void testsubmittingTimesheet() {
	        LocalDate weekStartDate = LocalDate.parse("2024-03-05");
	        WeekAndDayDto weekAndDayDto = WeekAndDayDto.builder()
	                .timesheetWeekId(1)
	                .timesheetStatus(1)
	                .weekStartDate(Date.valueOf(weekStartDate))
	                .build();

	        // Mock the behavior of the timeSheetDataService.submittingTimesheet() method
	        when(timeSheetDataService.submittingTimesheet("2024-05-03", 1))
	                .thenReturn(Collections.singletonList(TimesheetWeekEntity.builder().accountId(1).build()));

	        // Calling the method to test
	        List<TimesheetWeekEntity> actualResultList = timeSheetDataService.submittingTimesheet("2024-05-03", 1);

	        // Assertions
	        assertThat(actualResultList).isNotNull();
	        assertThat(actualResultList).hasSize(1);
	        assertThat(actualResultList.get(0).getAccountId()).isEqualTo(1);
	        // Add more assertions as needed
	    }
	
		
	}


	

