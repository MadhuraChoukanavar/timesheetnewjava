package com.feuji.accountprojectservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.accountprojectservice.bean.AccountTaskBean;
import com.feuji.accountprojectservice.exception.IdNotFoundException;
import com.feuji.accountprojectservice.repository.AccountProjectTaskRepo;
import com.feuji.accountprojectservice.service.AccountProjectTaskService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/accountProjects")
public class AccountTaskController {
	
	private static Logger log = LoggerFactory.getLogger(AccountProjectsController.class);

	@Autowired
	AccountProjectTaskRepo accountProjectTaskRepo;
	
	@Autowired
	AccountProjectTaskService accountProjectTaskService;
	
	@GetMapping(path = "/getbyid/{task_id}")
	public ResponseEntity<AccountTaskBean> getById(@PathVariable Integer task_id) throws IdNotFoundException {
		try {
			log.info("start getbyid method trainingid" + task_id);
			AccountTaskBean getbyid = accountProjectTaskService.getById(task_id);
			ResponseEntity<AccountTaskBean> responseEntity = new ResponseEntity<>(getbyid, HttpStatus.OK);
			log.info("end getting id details");
			return responseEntity;
		} catch (IdNotFoundException e) {
			throw new IdNotFoundException("Invalid taskid");
		}
	}

}
