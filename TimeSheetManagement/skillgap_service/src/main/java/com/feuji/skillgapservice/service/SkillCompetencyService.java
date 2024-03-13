package com.feuji.skillgapservice.service;

import java.util.Optional;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.entity.SkillCompetencyEntity;

public interface SkillCompetencyService {
	public void save(SkillCompetencyBean SkillCompetency);
	public SkillCompetencyBean updateAllRecords(SkillCompetencyBean skillCompetencyBean);
	public SkillCompetencyBean findByUuid(String uuid);

}
