package com.feuji.timesheetentryservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.feuji.timesheetentryservice.entity.HolidayEntity;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {
@Modifying
@Query(value="update holiday set is_deleted=1 where holiday_id=:holidayId",nativeQuery=true)
void updateIsDeleted(Integer holidayId);
}
