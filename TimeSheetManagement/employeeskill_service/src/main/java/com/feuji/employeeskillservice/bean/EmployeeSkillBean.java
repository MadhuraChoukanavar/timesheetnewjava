package com.feuji.employeeskillservice.bean;

import java.sql.Timestamp;

import com.feuji.employeeskillservice.entity.CommonReferenceDetailsEntity;
import com.feuji.employeeskillservice.entity.EmployeeEntity;
import com.feuji.employeeskillservice.entity.SkillEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeSkillBean 
{
	private Long employeeSkillId;
	private EmployeeEntity employeeId;
	private EmployeeEntity employeeCode;
	private SkillEntity skillId;
	private CommonReferenceDetailsEntity competencyLevelId;
	private CommonReferenceDetailsEntity skillTypeId;
	private int yearsOfExp;
	private byte certification;
	private String description;
	private String comments;	
	private byte isDeleted;
	private String uuid;
	private String createdBy;
	private Timestamp createdOn;
	private String modifiedBy;
	private Timestamp modifiedOn;
}
