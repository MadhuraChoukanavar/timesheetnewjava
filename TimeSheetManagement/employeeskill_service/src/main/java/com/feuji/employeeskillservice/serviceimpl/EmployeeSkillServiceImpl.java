package com.feuji.employeeskillservice.serviceimpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.repository.EmployeeSkillRepository;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeSkillServiceImpl implements EmployeeSkillService 
{
	@Autowired
	private EmployeeSkillRepository repository;
	@Autowired
	private GenerateUUID generateUUID;
	
	@Override
	public EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean)
	{
		log.info("save() in EmployeeSkillServiceImpl started");
		EmployeeSkillEntity skillEntity =null;
		EmployeeSkillBean skillBean=null;
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(bean);
			String generateUniqueId = generateUUID.generateUniqueId();
			log.info("generated uuid"+generateUniqueId);
			entity.setUuid(generateUniqueId);
			if (entity.getModifiedOn() == null) {
				entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
	        }
			if (entity.getCreatedOn() == null) {
				entity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
	        }

			
			skillEntity = repository.save(entity);	
			skillBean = entityToBeanCovertion(skillEntity);
			
		}catch(Exception e)
		{
			log.info("Error occurred while saving employee in implementation: {}",e.getMessage());		
		}
		log.info("save() in EmployeeSkillServiceImpl ended");
		return skillBean;
	}

	private EmployeeSkillBean entityToBeanCovertion(EmployeeSkillEntity entity)
	{
		log.info("entityToBeanCovertion() started");
		EmployeeSkillBean bean = new EmployeeSkillBean();
		bean.setEmployeeSkillId(entity.getEmployeeSkillId());
		bean.setEmployeeId(entity.getEmployeeId());
		bean.setEmployeeCode(entity.getEmployeeCode());
		bean.setSkillId(entity.getSkillId());
		bean.setCompetencyLevelId(entity.getCompetencyLevelId());
		bean.setSkillTypeId(entity.getSkillTypeId());
		bean.setYearsOfExp(entity.getYearsOfExp());
		bean.setCertification(entity.getCertification());
		bean.setDescription(entity.getDescription());
		bean.setComments(entity.getComments());
		bean.setIsDeleted(entity.getIsDeleted());
		bean.setUuid(entity.getUuid());
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		log.info("entityToBeanCovertion() ended");
		return bean;
	}
	private EmployeeSkillEntity beanToEntityConvertion(EmployeeSkillBean bean)
	{
		log.info("beanToEntityConvertion() started");
		EmployeeSkillEntity entity = new EmployeeSkillEntity();
		
		entity.setEmployeeSkillId(bean.getEmployeeSkillId());
		entity.setEmployeeId(bean.getEmployeeId());
		entity.setEmployeeCode(bean.getEmployeeCode());
		entity.setSkillId(bean.getSkillId());
		entity.setSkillTypeId(bean.getSkillTypeId());
		entity.setCompetencyLevelId(bean.getCompetencyLevelId());
		entity.setYearsOfExp(bean.getYearsOfExp());
		entity.setUuid(bean.getUuid());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setCertification(bean.getCertification());
		entity.setDescription(bean.getDescription());
		entity.setComments(bean.getComments());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());
		log.info("beanToEntityConvertion() ended");
		return entity;
	}

//	@Override
//	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException
//	{
//		log.info("getEmployeeSkill() in implementation started");
//			List<Optional<EmployeeSkillEntity>> findByEmployeeId = repository.findByEmployeeId(employeeId);
//			
//			List<EmployeeSkillBean> beanList = new ArrayList<>();
//			if(beanList.size()==0)
//				throw new NoRecordFoundException("no record found with id: "+employeeId);
//			
//			for (Optional<EmployeeSkillEntity> employeeSkillBean : findByEmployeeId) {
//				EmployeeSkillEntity employeeSkillEntity = employeeSkillBean.get();
//				beanList.add(entityToBeanCovertion(employeeSkillEntity));
//			}
//			log.info("getEmployeeSkill() in implementation ended");
//			return beanList;
//	}

	@Override
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillBean employeeSkillBean) {
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(employeeSkillBean);
			entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			EmployeeSkillEntity save = repository.save(entity);
			EmployeeSkillBean entityToBeanCovertion = entityToBeanCovertion(save);
			return entityToBeanCovertion;
		}
		catch (Exception e) {
			throw new NullPointerException("employee skill bean object is null");
		}
		
	}

	@Override
	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
