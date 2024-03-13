package com.feuji.skillgapservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.skillgapservice.entity.SkillCompetencyEntity;

import jakarta.transaction.Transactional;
@Transactional
public interface SkillCompetencyRepository extends JpaRepository<SkillCompetencyEntity, Long>{

	Optional<SkillCompetencyEntity> findByUuid(String uuid);

}
