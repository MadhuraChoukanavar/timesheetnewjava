package com.feuji.employeeservice.service;

import java.util.List;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.entity.CommonReferenceDetailsEntity;
import com.feuji.employeeservice.entity.EmployeeEntity;

public interface EmployeeService {

	public EmployeeEntity saveEmployee(EmployeeBean employeeBean);

	public EmployeeEntity getById(Integer id);

	public List<EmployeeDto> getByUserEmpId(Integer id);

	public EmployeeEntity getByUuid(String uuid);

	public void updateEmployeeDetails(EmployeeEntity updateEmpolyee, Integer id) throws Throwable;

	public boolean isEmployeeCodeUnique(String empCode);

	public EmployeeBean getReportingMngIdByEmpId(Integer id);

	public List<AddEmployee> getGender(Integer referenceTypeId);

	public List<AddEmployee> getEmploymentType(Integer referenceTypeId);

	public List<AddEmployee> getDesignation(Integer referenceTypeId);

	public List<AddEmployee> getBusinessUnit(Integer referenceTypeId);
	
	public List<AddEmployee> getByReferenceTypeId(Integer referenceTypeId);
}