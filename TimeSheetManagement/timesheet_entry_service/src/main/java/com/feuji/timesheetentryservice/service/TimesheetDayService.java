package com.feuji.timesheetentryservice.service;

import java.util.List;

import com.feuji.timesheetentryservice.bean.CommonReferenceDetailsBean;
import com.feuji.timesheetentryservice.bean.TimesheetDayBean;
import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;

public interface TimesheetDayService {

	 TimesheetDayEntity saveTimesheetDay(TimesheetDayBean timesheetDayBean);

	 TimesheetDayEntity getTimeSheetDayByuuid(Integer id);

	 List<CommonReferenceDetailsBean> getDetailsByTypeId(String typeName);
}
