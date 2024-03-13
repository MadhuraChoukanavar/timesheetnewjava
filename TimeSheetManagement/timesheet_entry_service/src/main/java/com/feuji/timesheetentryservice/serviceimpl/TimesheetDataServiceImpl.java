package com.feuji.timesheetentryservice.serviceimpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.timesheetentryservice.bean.AccountProjectTaskTypeBean;
import com.feuji.timesheetentryservice.bean.AccountProjectsBean;
import com.feuji.timesheetentryservice.bean.AccountTaskBean;
import com.feuji.timesheetentryservice.bean.CommonReferenceDetailsBean;
import com.feuji.timesheetentryservice.bean.EmployeeBean;
import com.feuji.timesheetentryservice.bean.WeekAndDayDataBean;
import com.feuji.timesheetentryservice.dto.TimesheetWeekDayDetailDto;
import com.feuji.timesheetentryservice.dto.WeekAndDayDto;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;
import com.feuji.timesheetentryservice.entity.TimesheetWeekEntity;
import com.feuji.timesheetentryservice.repository.TimesheetDayRepo;
import com.feuji.timesheetentryservice.repository.TimesheetWeekRepo;
import com.feuji.timesheetentryservice.service.TimeSheetDataService;
import com.feuji.timesheetentryservice.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TimesheetDataServiceImpl implements TimeSheetDataService {

	private static Logger log = LoggerFactory.getLogger(TimesheetDayServiceImpl.class);
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	TimesheetDayRepo timesheetDayRepo;

	@Autowired
	TimesheetWeekRepo timesheetWeekRepo;

	@Override
	public List<TimesheetWeekEntity> saveAll(List<WeekAndDayDataBean> weekAndDayDataBeans) {
		List<TimesheetWeekEntity> weekEntityList = new ArrayList<>();

		LocalDate currentDate = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		int currentWeekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());

		Date dateMon = weekAndDayDataBeans.get(0).getDateMon();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		String formattedDate = dateFormat.format(dateMon);

		Date monDate = convertDateStringToDate(formattedDate);
		System.out.println(monDate);
		for (WeekAndDayDataBean weekAndDayDataBean : weekAndDayDataBeans) {
			Integer employeeId = weekAndDayDataBean.getEmployeeId();
			Integer accountId = weekAndDayDataBean.getAccountId();
			
			List<Integer> getprojectIdOfWeek = timesheetWeekRepo.getprojectIdOfWeek(employeeId, accountId, monDate);
			
			if (getprojectIdOfWeek.size() == 0 || !getprojectIdOfWeek.contains(weekAndDayDataBean.getProjectId())) {
			
				TimesheetWeekEntity timesheetWeekEntity = createTimesheetWeekEntity(currentWeekNumber,
						weekAndDayDataBean);
				weekEntityList.add(timesheetWeekEntity);
				timesheetWeekRepo.save(timesheetWeekEntity);

				ArrayList<Date> date = new ArrayList<>(List.of(weekAndDayDataBean.getDateMon(),
						weekAndDayDataBean.getDateTue(), weekAndDayDataBean.getDateWed(),
						weekAndDayDataBean.getDateThu(), weekAndDayDataBean.getDateFri(),
						weekAndDayDataBean.getDateSat(), weekAndDayDataBean.getDateSun()));

				ArrayList<Integer> num = new ArrayList<>(List.of(weekAndDayDataBean.getHoursMon(),
						weekAndDayDataBean.getHoursTue(), weekAndDayDataBean.getHoursWed(),
						weekAndDayDataBean.getHoursThu(), weekAndDayDataBean.getHoursFri(),
						weekAndDayDataBean.getHoursSat(), weekAndDayDataBean.getHoursSun()));

				for (int j = 0; j < date.size(); j++) {
					if (num.get(j) != 0) {
						TimesheetDayEntity timeDayEntity = createTimesheetDayEntity(timesheetWeekEntity,
								weekAndDayDataBean, date.get(j), num.get(j));
						timesheetDayRepo.save(timeDayEntity);

					}
				}
				
			}

			else {
				
				Integer timesheetweekId = timesheetWeekRepo.getTimesheetweekId(weekAndDayDataBean.getProjectId(),
						monDate, weekAndDayDataBean.getEmployeeId());
				TimesheetWeekEntity timesheetWeekEntity = timesheetWeekRepo.findById(timesheetweekId).get();
				weekEntityList.add(timesheetWeekEntity);

				ArrayList<Date> date = new ArrayList<>(List.of(weekAndDayDataBean.getDateMon(),
						weekAndDayDataBean.getDateTue(), weekAndDayDataBean.getDateWed(),
						weekAndDayDataBean.getDateThu(), weekAndDayDataBean.getDateFri(),
						weekAndDayDataBean.getDateSat(), weekAndDayDataBean.getDateSun()));

				ArrayList<Integer> num = new ArrayList<>(List.of(weekAndDayDataBean.getHoursMon(),
						weekAndDayDataBean.getHoursTue(), weekAndDayDataBean.getHoursWed(),
						weekAndDayDataBean.getHoursThu(), weekAndDayDataBean.getHoursFri(),
						weekAndDayDataBean.getHoursSat(), weekAndDayDataBean.getHoursSun()));

				for (int j = 0; j < date.size(); j++) {
					if (num.get(j) != 0) {
						TimesheetDayEntity timeDayEntity = createTimesheetDayEntity(timesheetWeekEntity,
								weekAndDayDataBean, date.get(j), num.get(j));
						timesheetDayRepo.save(timeDayEntity);
					
					}

				}
			
			}

		}

		return weekEntityList;

	}

	private TimesheetWeekEntity createTimesheetWeekEntity(int currentWeekNumber, WeekAndDayDataBean weekDayData) {
		TimesheetWeekEntity timesheetWeekEntity = new TimesheetWeekEntity();
		timesheetWeekEntity.setEmployeeId(weekDayData.getEmployeeId());
		timesheetWeekEntity.setAccountProjectId(weekDayData.getProjectId());
		timesheetWeekEntity.setAccountId(getAccountIdFromProjectId(weekDayData.getProjectId()).getAccountId());
		timesheetWeekEntity
				.setApprovedBy(getEmployeeManagerByEmpId(weekDayData.getEmployeeId()).getReportingManagerId());
		timesheetWeekEntity.setWeekNumber(currentWeekNumber - 1);
		Date startDate = weekDayData.getDateMon();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		timesheetWeekEntity.setWeekStartDate(startDate);
		Date endDate = weekDayData.getDateSun();
		endDate.setHours(0);
		endDate.setMinutes(0);
		endDate.setSeconds(0);
		timesheetWeekEntity.setWeekEndDate(endDate);
		timesheetWeekEntity.setTimesheetStatus(Constants.TIME_SHEET_STATUS_SAVED);
		timesheetWeekEntity.setComments(weekDayData.getComments());
		timesheetWeekEntity.setCreatedBy(getEmployeeManagerByEmpId(weekDayData.getEmployeeId()).getFirstName());
		timesheetWeekEntity.setModifiedBy(getEmployeeManagerByEmpId(weekDayData.getEmployeeId()).getFirstName());
		timesheetWeekEntity.setIsDeleted((byte) 0);
		timesheetWeekEntity.setIsactive((byte) 0);
		return timesheetWeekEntity;
	}

	private TimesheetDayEntity createTimesheetDayEntity(TimesheetWeekEntity timesheetWeekEntity,
			WeekAndDayDataBean yourJavaClass, Date date, int hours) {

		TimesheetDayEntity timeDayEntity = new TimesheetDayEntity();
		timeDayEntity.setTimesheetWeekEntity(timesheetWeekEntity);
		timeDayEntity.setDate(date);
		timeDayEntity.setNumberOfHours(hours);
		timeDayEntity.setAttendanceType(yourJavaClass.getAttendanceType());
		timeDayEntity.setTaskId(yourJavaClass.getTaskId());
		timeDayEntity.setCreatedBy(getEmployeeManagerByEmpId(yourJavaClass.getEmployeeId()).getFirstName());
		timeDayEntity.setModifiedBy(getEmployeeManagerByEmpId(yourJavaClass.getEmployeeId()).getFirstName());
		timeDayEntity.setIsDeleted((byte) 0);
		timeDayEntity.setIsActive((byte) 0);

		return timeDayEntity;
	}

	public EmployeeBean getEmployeeManagerByEmpId(Integer employeeId) {
		log.info("Connecting to Employee server...");
		String url = "http://localhost:8082/api/employee/getReportingMngIdByEmpId/" + employeeId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
	
		ResponseEntity<EmployeeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				EmployeeBean.class);
		
		EmployeeBean employeeBean = responseEntity.getBody();
	
		return employeeBean;
	}

	public AccountProjectsBean getAccountIdFromProjectId(Integer accountId) {
		log.info("Connecting to AccountProject server...");
		String url = "http://localhost:8083/api/accountProjects/getAccountProject/" + accountId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountProjectsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				AccountProjectsBean.class);

		AccountProjectsBean accountProjectsBean = responseEntity.getBody();
		return accountProjectsBean;

	}

	public AccountProjectTaskTypeBean getAccountTaskType(Integer taskTypeId) {
		log.info("Connecting to other server...");
		String url = "http://localhost:8083/api/accountProjectTaskType/" + taskTypeId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountProjectTaskTypeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				httpEntity, AccountProjectTaskTypeBean.class);

		AccountProjectTaskTypeBean accountProjectsTaskTypeBean = responseEntity.getBody();
		return accountProjectsTaskTypeBean;

	}

	public AccountTaskBean getAccountTask(Integer taskId) {
		log.info("Connecting to other server...");
		String url = "http://localhost:8083/api/accountProjects/getbyid/" + taskId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountTaskBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				AccountTaskBean.class);

		AccountTaskBean accountTaskBean = responseEntity.getBody();
		return accountTaskBean;

	}

	public CommonReferenceDetailsBean getAttendanceType(Integer attendanceTypeId) {
		 try {
		        log.info("Connecting to CommonRefarance  server...");
		        String url = "http://localhost:8089/api/referencedetails/getbyid/" + attendanceTypeId;

		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);

		        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		        ResponseEntity<CommonReferenceDetailsBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
		                httpEntity, CommonReferenceDetailsBean.class);

		        CommonReferenceDetailsBean commonDetailsBean = responseEntity.getBody();
		        return commonDetailsBean;
		    } catch (Exception e) {
		       
		        log.error("Client error while connecting to the server: {}", e.getMessage(), e);
		        
		        return null;
		    }

	}

	public List<WeekAndDayDto> fetchAllWeekDayRecordsById(Integer accountId, Integer employeeId, String weekStartDate,
			String weekEndDate) {
		log.info("fetching All Week Day Data" + employeeId + accountId);

		Date startDate = convertDateStringToDate(weekStartDate);
		Date endDate = convertDateStringToDate(weekEndDate);

		try {

			List<TimesheetWeekDayDetailDto> timesheetWeekDayDetails = timesheetWeekRepo
					.findTimesheetDetailsByDateRange(accountId, employeeId, startDate, endDate);

			List<WeekAndDayDto> weekAndDayDtoList = new ArrayList<>();
			Map<String, WeekAndDayDto> weekDayMap = new TreeMap<>();

			for (TimesheetWeekDayDetailDto timesheetWeekDayDetailDto : timesheetWeekDayDetails) {

				String key = "" + timesheetWeekDayDetailDto.getEmployeeId()
						+ timesheetWeekDayDetailDto.getAccountProjectId() + timesheetWeekDayDetailDto.getTaskTypeId()
						+ timesheetWeekDayDetailDto.getTaskId() + timesheetWeekDayDetailDto.getAttendanceType();

				if (weekDayMap.containsKey(key)) {
					Timestamp date = timesheetWeekDayDetailDto.getDate();
					String dayOfWeek = getDayOfWeek(date);

					WeekAndDayDto weekAndDayDto = weekDayMap.get(key);
					if (dayOfWeek.equalsIgnoreCase("TUESDAY")) {
						weekAndDayDto.setHoursTue(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateTue(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("MONDAY")) {
						weekAndDayDto.setHoursMon(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateMon(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("WEDNESDAY")) {
						weekAndDayDto.setHoursWed(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateWed(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("THURSDAY")) {
						weekAndDayDto.setHoursThu(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateThu(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("FRIDAY")) {
						weekAndDayDto.setHoursFri(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateFri(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("SATURDAY")) {
						weekAndDayDto.setHoursSat(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateSat(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("SUNDAY")) {
						weekAndDayDto.setHoursSun(timesheetWeekDayDetailDto.getNumberOfHours());
						weekAndDayDto.setDateSun(timesheetWeekDayDetailDto.getDate());
					}
				} else {

					Timestamp date = timesheetWeekDayDetailDto.getDate();
					String dayOfWeek = getDayOfWeek(date);

					WeekAndDayDto timesheetWeekDayDto = WeekAndDayDto.builder()
							.timesheetWeekId(timesheetWeekDayDetailDto.getTimesheetWeekId())
							.employeeId(timesheetWeekDayDetailDto.getEmployeeId())
							.accountId(timesheetWeekDayDetailDto.getAccountId())
							.projectId(timesheetWeekDayDetailDto.getAccountProjectId())
							.projectName(getAccountIdFromProjectId(timesheetWeekDayDetailDto.getAccountProjectId())
									.getProjectName())
							.taskId(timesheetWeekDayDetailDto.getTaskId())
							.taskName(getAccountTask(timesheetWeekDayDetailDto.getTaskId()).getTask())
							.taskTypeId(timesheetWeekDayDetailDto.getTaskTypeId())
							.taskTypeName(getAccountTaskType(timesheetWeekDayDetailDto.getTaskTypeId()).getTaskType())
							.attendanceTypeName(getAttendanceType(timesheetWeekDayDetailDto.getAttendanceType())
									.getReferenceDetailValue())
							.attendanceType(timesheetWeekDayDetailDto.getAttendanceType())

							.weekStartDate(timesheetWeekDayDetailDto.getWeekStartDate()).build();
					if (dayOfWeek.equalsIgnoreCase("TUESDAY")) {
						timesheetWeekDayDto.setHoursTue(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateTue(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("MONDAY")) {
						timesheetWeekDayDto.setHoursMon(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateMon(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("WEDNESDAY")) {
						timesheetWeekDayDto.setHoursWed(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateWed(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("THURSDAY")) {
						timesheetWeekDayDto.setHoursThu(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateThu(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("FRIDAY")) {
						timesheetWeekDayDto.setHoursFri(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateFri(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("SATURDAY")) {
						timesheetWeekDayDto.setHoursSat(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateSat(timesheetWeekDayDetailDto.getDate());
					}
					if (dayOfWeek.equalsIgnoreCase("SUNDAY")) {
						timesheetWeekDayDto.setHoursSun(timesheetWeekDayDetailDto.getNumberOfHours());
						timesheetWeekDayDto.setDateSun(timesheetWeekDayDetailDto.getDate());
					}

					weekDayMap.put(key, timesheetWeekDayDto);
					weekAndDayDtoList.add(timesheetWeekDayDto);
				}

			}
		
			return weekAndDayDtoList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getDayOfWeek(Timestamp timestamp) {
		try {
			

			Instant instant = timestamp.toInstant();

			DayOfWeek dayOfWeek = instant.atZone(java.time.ZoneOffset.UTC).getDayOfWeek();

			return dayOfWeek.name();
		} catch (Exception e) {
			System.err.println("Error getting day of the week: " + e.getMessage());
			return null;
		}
	}


	private static List<LocalDate> getWeekDates(LocalDate weekStartDate) {
	    List<LocalDate> weekDates = new ArrayList<>();

	    try {
	        for (int i = 0; i < DayOfWeek.values().length; i++) {
	            LocalDate currentDate = weekStartDate.plusDays(i);
	            weekDates.add(currentDate);
	        }
	    } catch (DateTimeException e) {

	        System.err.println("Error occurred while calculating week dates: " + e.getMessage());
	       
	        throw new RuntimeException("Failed to calculate week dates", e);
	    }

	
	    return weekDates;
	}
	@Override
	public List<TimesheetDayEntity> deleteDayRecord(WeekAndDayDto weekAndDayDto) {

		List<TimesheetDayEntity> timesheetDayEntityList = new ArrayList<>();
		Integer timesheetWeekId = weekAndDayDto.getTimesheetWeekId();

		List<TimesheetDayEntity> listOfTimesheetDayEntity = timesheetDayRepo
				.findAllByTimesheetWeekEntityTimesheetWeekId(timesheetWeekId);

		for (TimesheetDayEntity timesheetDayEntity : listOfTimesheetDayEntity) {

			if (weekAndDayDto.getTaskId().equals(timesheetDayEntity.getTaskId())
					&& weekAndDayDto.getAttendanceType().equals(timesheetDayEntity.getAttendanceType())) {

				timesheetDayEntity.setIsDeleted((byte) 1);

				timesheetDayRepo.save(timesheetDayEntity);

			}
			List<TimesheetDayEntity> listOfTimesheetDayEntity2 = timesheetDayRepo
					.findAllByTimesheetWeekEntityTimesheetWeekId(timesheetWeekId);
			if (listOfTimesheetDayEntity2.size() <= 0) {

				TimesheetWeekEntity timesheetWeekEntity = timesheetWeekRepo.findById(timesheetWeekId).orElseThrow();
				timesheetWeekEntity.setIsDeleted((byte) 1);
				timesheetWeekRepo.save(timesheetWeekEntity);

			}

		}

		return timesheetDayEntityList;
	}

	@Override
	public List<TimesheetWeekEntity> submittingTimesheet(String weekStartDate, Integer timesheetStatus) {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date convertedWeekStartDate = dateFormat.parse(weekStartDate);

			List<TimesheetWeekEntity> findByWeekStartDate = timesheetWeekRepo
					.findByWeekStartDate(convertedWeekStartDate);

			for (TimesheetWeekEntity timesheetWeekEntity : findByWeekStartDate) {
				timesheetWeekEntity.setTimesheetStatus(timesheetStatus);

			}

			return findByWeekStartDate;
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
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
}
