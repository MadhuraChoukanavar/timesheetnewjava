package com.feuji.employeeskillservice.entity;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee_skills")
public class EmployeeSkillEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_skill_id")
	private Long employeeSkillId;
	
	@OneToOne
	@JoinColumn(name="employee_id" ,referencedColumnName="employee_id")
	private EmployeeEntity employeeId;
	
	@OneToOne
	@JoinColumn(name="employee_code" ,referencedColumnName="employee_code")
	private EmployeeEntity employeeCode;
	
	@OneToOne
	@JoinColumn(name="skill_id",referencedColumnName="skill_id")
	private SkillEntity skillId;
	
	@OneToOne
	@JoinColumn(name="competency_level_id",referencedColumnName="reference_type_id")
	private CommonReferenceDetailsEntity competencyLevelId;
	
	@OneToOne
	@JoinColumn(name="skill_type_id",referencedColumnName="reference_type_id")
	private CommonReferenceDetailsEntity skillTypeId;
	
	@Column(name="years_of_experiance")
	private int yearsOfExp;
	
	@Column(name="certification")
	private byte certification;
	
	@Column(name="description")
	private String description;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="is_deleted")
	private byte isDeleted;
	
	@Column(name="uuid")
	private String uuid;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_on")
	private Timestamp createdOn;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="modified_on")
	private Timestamp modifiedOn;
}
