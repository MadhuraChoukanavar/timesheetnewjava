package com.feuji.skillgapservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.feuji.skillgapservice.entity.SkillEntity;


public interface SkillRepository extends JpaRepository<SkillEntity, Integer> {
	
	Optional<SkillEntity>findByUuid(String uuid);

}
