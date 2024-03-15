package com.feuji.timesheetentryservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.feuji.timesheetentryservice.entity.TimesheetDayEntity;

public interface TimesheetDayRepo extends JpaRepository<TimesheetDayEntity, Integer> {
	public TimesheetDayEntity deleteByTimesheetWeekEntityTimesheetWeekId(Integer timesheetWeekId);

	public List<TimesheetDayEntity> findAllByTimesheetWeekEntityTimesheetWeekId(Integer timesheetWeekId);

	@Query("SELECT p FROM TimesheetDayEntity p WHERE p.timesheetWeekEntity.timesheetWeekId = ?1 AND p.attendanceType = ?2 AND p.taskId = ?3")
	List<TimesheetDayEntity> findByWeekIdAndAttendanceTypeAndTaskId(Integer timesheetWeekId, Integer attendanceType,
			Integer taskId);

	@Query("SELECT p FROM TimesheetDayEntity p WHERE p.date = :dateOfWeek")
	TimesheetDayEntity findByDate(@Param("dateOfWeek") Date dateOfWeek);

}
