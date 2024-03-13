package com.feuji.skillgapservice.serviceimpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.entity.SkillEntity;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.repository.SkillRepository;
import com.feuji.skillgapservice.service.SkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

	@Autowired
	public SkillRepository skillRepository;
//	GenerateUUID generateUUID = new GenerateUUID();

	// conversion entity to bean and visa versa

	public SkillBean entityToBean(SkillEntity entity) {
		SkillBean skillBean = new SkillBean();

		skillBean.setSkillId(entity.getSkillId());
		skillBean.setSkillName(entity.getSkillName());
		skillBean.setTechinicalCategoryId(entity.getTechinicalCategoryId());
		skillBean.setSkillCategoryId(entity.getSkillCategoryId());
		skillBean.setDescription(entity.getDescription());
		skillBean.setIsDeleted(entity.getIsDeleted());
		skillBean.setUuid(entity.getUuid());
		skillBean.setCreatedBy(entity.getCreatedBy());
		skillBean.setCreatedOn(entity.getCreatedOn());
		skillBean.setModifiedBy(entity.getModifiedBy());
		skillBean.setModifiedOn(entity.getModifiedOn());

		return skillBean;
	}

	public SkillEntity beanToEntity(SkillBean bean) {
		SkillEntity entity = new SkillEntity();

		entity.setSkillId(bean.getSkillId());
		entity.setSkillName(bean.getSkillName());
		entity.setTechinicalCategoryId(bean.getTechinicalCategoryId());
		entity.setSkillCategoryId(bean.getSkillCategoryId());
		entity.setDescription(bean.getDescription());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setUuid(bean.getUuid());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());

		return entity;
	}

	@Override
	public SkillBean saveSkill(SkillBean skillBean) {
		log.info("service start");
		try {
			SkillEntity entity = beanToEntity(skillBean);
//			if (beanToEntity(skillBean).getUuid() == null) {
//			
//				String newUuid = generateUUID.generateUniqueId();
//				log.info(newUuid);
//				entity.setUuid(newUuid);
//
//			}
			if (beanToEntity(skillBean).getCreatedOn() == null) {
				
				entity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				
			}
			if (beanToEntity(skillBean).getModifiedOn() == null) {
				
				entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
				
			}

			SkillEntity save = skillRepository.save(entity);

			return entityToBean(save);

		} catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		}
	}

	@Override
	public SkillBean getByUuid(String uuid) {
		SkillEntity skillEntity = skillRepository.findByUuid(uuid)
				.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + uuid));
		return entityToBean(skillEntity);

	}

	


	@Override
	public SkillBean updateDetails(SkillBean skillBean) {
	    try {
	        Optional<SkillEntity> optionalEntity = skillRepository.findByUuid(skillBean.getUuid());
	        if (optionalEntity.isPresent()) {
	            SkillEntity entity = optionalEntity.get();
	            
	            // Update only the necessary fields
	            entity.setDescription(skillBean.getDescription());
	            entity.setModifiedBy(skillBean.getModifiedBy());
	            entity.setIsDeleted(skillBean.getIsDeleted());
	            
	            // Update modified timestamp if it's not provided
	            if (entity.getModifiedOn() == null) {
	                entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
	            }

	            SkillEntity savedEntity = skillRepository.save(entity);
	            return entityToBean(savedEntity);
	        } else {
	            throw new SkillNotFoundException("Skill not found with UUID: " + skillBean.getUuid());
	        }
	    } catch (SkillNotFoundException ex) {
	        log.error("Invalid skill UUID", ex);
	        throw ex;
	    }
	}

	@Override
	public List<SkillBean> getAllSkills() {
	    List<SkillEntity> skillEntities = skillRepository.findAll();
	    List<SkillBean> skillBeans = new ArrayList<>();

	    for (SkillEntity entity : skillEntities) {
	        skillBeans.add(entityToBean(entity));
	    }

	    return skillBeans;
	}



	

}
