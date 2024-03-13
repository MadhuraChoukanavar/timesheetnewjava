package com.feuji.employeeskillservice.service;

import java.util.List;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;

public interface EmployeeSkillService 
{
	EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean);

	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException;
	
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillBean employeeSkillBean) ;
}
