package com.feuji.timesheetentryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;

public interface TimesheetDayRepo  extends JpaRepository<TimesheetDayEntity, Integer>{
    public TimesheetDayEntity deleteByTimesheetWeekEntityTimesheetWeekId(Integer timesheetWeekId);
    public List<TimesheetDayEntity> findAllByTimesheetWeekEntityTimesheetWeekId(Integer timesheetWeekId);

}
