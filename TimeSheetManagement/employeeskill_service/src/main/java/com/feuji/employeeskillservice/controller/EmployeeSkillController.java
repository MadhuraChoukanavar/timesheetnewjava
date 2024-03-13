package com.feuji.employeeskillservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/employeeskill")
public class EmployeeSkillController 
{
	@Autowired
	private EmployeeSkillService service;
	
	@GetMapping("/check")
	public String checkUp()
	{
		log.info("checkUp method started");
		return "hello, Welcome to Feuji Firm Application!!!!!";
	}
	
	@PostMapping("/insert")
	public ResponseEntity<EmployeeSkillBean> insert(@RequestBody EmployeeSkillBean bean)
	{
		log.info("insert() in controller start");
		try {
			EmployeeSkillBean skillBean = service.saveEmployeeSkill(bean);
			return new ResponseEntity<>(skillBean,HttpStatus.CREATED);
		}catch(Exception e) {
			log.error("Error occurred while saving employee: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getEmployeeSkillById/{employeeId}")
	public ResponseEntity<List<EmployeeSkillBean>> getEmployeeSkill(@PathVariable Long employeeId) throws NoRecordFoundException  
	{
		log.info("getEmployeeSkill() in controller start");
		log.info("EmployeeId-"+employeeId);
		try {
			List<EmployeeSkillBean> list=service.getEmployeeSkillById(employeeId);
			log.info("insert() in controller end");
			return new ResponseEntity<List<EmployeeSkillBean>>(list,HttpStatus.FOUND);
		}catch(NoRecordFoundException e)
		{
			throw new NoRecordFoundException(e.getMessage());
		}
	}
	
	@PostMapping("/updateEmployeeSkillByUUID")
	public ResponseEntity<EmployeeSkillBean> updateEmployeeSkill(@RequestBody EmployeeSkillBean employeeSkillBean)  
	{
		log.info("updateEmployeeSkill() in controller start");
		log.info("EmployeeSkillbean object"+employeeSkillBean);
		try {
			EmployeeSkillBean updateEmployeeSkill = service.updateEmployeeSkill(employeeSkillBean);
			log.info("insert() in controller end");
			return new ResponseEntity<EmployeeSkillBean>(updateEmployeeSkill,HttpStatus.OK);
		}catch(Exception e)
		{
			throw new NullPointerException("employee object is null");
		}
	}
	
	
}
