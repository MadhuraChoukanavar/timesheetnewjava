package com.feuji.skillgapservice.serviceImplementation;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.entity.SkillCompetencyEntity;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.repository.SkillCompetencyRepository;
import com.feuji.skillgapservice.service.SkillCompetencyService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SkillCompetencyServiceImplementation implements SkillCompetencyService{
	
	@Autowired
	private GenerateUUID generateUUID;
	@Autowired
	SkillCompetencyRepository competencyRepository;
	public SkillCompetencyEntity convertBeanToEntity(SkillCompetencyBean skillCompetencyBean)
	{
		SkillCompetencyEntity skillCompetencyEntity=new SkillCompetencyEntity();
		skillCompetencyEntity.setRoleId(skillCompetencyBean.getRoleId());
		skillCompetencyEntity.setRoleName(skillCompetencyBean.getRoleName());
		skillCompetencyEntity.setSkillId(skillCompetencyBean.getSkillId());
		skillCompetencyEntity.setSkillTypeId(skillCompetencyBean.getSkillTypeId());
		skillCompetencyEntity.setCompetencyLevelId(skillCompetencyBean.getCompetencyLevelId());
		skillCompetencyEntity.setYearsOfExperiance(skillCompetencyBean.getYearsOfExperiance());
		skillCompetencyEntity.setCertification(skillCompetencyBean.getCertification());
		skillCompetencyEntity.setDescription(skillCompetencyBean.getDescription());
		skillCompetencyEntity.setComments(skillCompetencyBean.getComments());
		skillCompetencyEntity.setIsDeleted(skillCompetencyBean.getIsDeleted());
		skillCompetencyEntity.setUuid(skillCompetencyBean.getUuid());
		skillCompetencyEntity.setCreatedBy(skillCompetencyBean.getCreatedBy());
		skillCompetencyEntity.setCreatedOn(skillCompetencyBean.getCreatedOn());
		skillCompetencyEntity.setModifiedBy(skillCompetencyBean.getModifiedBy());
		return skillCompetencyEntity;
	}
	
	public SkillCompetencyBean convertEntityToBean(SkillCompetencyEntity skillCompetencyEntity)
	{
		SkillCompetencyBean skillCompetencyBean=new SkillCompetencyBean();
		skillCompetencyBean.setRoleId(skillCompetencyEntity.getRoleId());
		skillCompetencyBean.setRoleName(skillCompetencyEntity.getRoleName());
		skillCompetencyBean.setSkillId(skillCompetencyEntity.getSkillId());
		skillCompetencyBean.setSkillTypeId(skillCompetencyEntity.getSkillTypeId());
		skillCompetencyBean.setCompetencyLevelId(skillCompetencyEntity.getCompetencyLevelId());
		skillCompetencyBean.setYearsOfExperiance(skillCompetencyEntity.getYearsOfExperiance());
		skillCompetencyBean.setCertification(skillCompetencyEntity.getCertification());
		skillCompetencyBean.setDescription(skillCompetencyEntity.getDescription());
		skillCompetencyBean.setComments(skillCompetencyEntity.getComments());
		skillCompetencyBean.setIsDeleted(skillCompetencyEntity.getIsDeleted());
		skillCompetencyBean.setUuid(skillCompetencyEntity.getUuid());
		skillCompetencyBean.setCreatedBy(skillCompetencyEntity.getCreatedBy());
		skillCompetencyBean.setCreatedOn(skillCompetencyEntity.getCreatedOn());
		skillCompetencyBean.setModifiedBy(skillCompetencyEntity.getModifiedBy());
		return skillCompetencyBean;
	}
	
	@Override
	public void save(SkillCompetencyBean skillCompetencyBean){ 
		
		if(skillCompetencyBean!=null)
		{
			SkillCompetencyEntity convertBeanToEntity = convertBeanToEntity(skillCompetencyBean);
//			if (convertBeanToEntity.getModifiedOn() == null) {
//				convertBeanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
//	        }
//			if (convertBeanToEntity.getCreatedOn() == null) {
//				convertBeanToEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
//	        }
//			if (convertBeanToEntity.getUuid() == null) {
//	            // Generate a new UUID
//	            String newUuid = generateUUID.generateUniqueId();
//	            convertBeanToEntity.setUuid(newUuid);
//	        }
			competencyRepository.save(convertBeanToEntity);
		}
		
		
	}

	@Override
	public SkillCompetencyBean updateAllRecords(SkillCompetencyBean skillCompetencyBean) {
			SkillCompetencyEntity beanToEntity = convertBeanToEntity(skillCompetencyBean);
			if (beanToEntity.getModifiedOn() == null) {
				beanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
	        }
			SkillCompetencyEntity save = competencyRepository.save(beanToEntity);
			SkillCompetencyBean entityToBean = convertEntityToBean(save);
			return entityToBean;
	}

	@Override
	public SkillCompetencyBean findByUuid(String uuid) {
		SkillCompetencyEntity skillEntity = competencyRepository.findByUuid(uuid)
				.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + uuid));
		return convertEntityToBean(skillEntity);

	}
}
