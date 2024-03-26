package com.feuji.accountprojectservice.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.accountprojectservice.bean.AccountBean;
import com.feuji.accountprojectservice.bean.AccountProjectsBean;
import com.feuji.accountprojectservice.bean.EmployeeBean;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;
import com.feuji.accountprojectservice.exception.UUIDNotFoundException;
import com.feuji.accountprojectservice.repository.AccountProjectsRepo;
import com.feuji.accountprojectservice.service.AccountProjectsService;

import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/accountProjects")
public class AccountProjectsController {
	private static Logger log = LoggerFactory.getLogger(AccountProjectsController.class);

	@Autowired
	AccountProjectsService accountProjectsService;
	
	@Autowired
	AccountProjectsRepo accountProjectsRepo;

	@PostMapping("/save")
	public ResponseEntity<AccountProjectsEntity> save(@RequestBody AccountProjectsBean accountProjectsBean) {
		try {
			log.info("Saving project started: {}", accountProjectsBean);
			AccountProjectsEntity saveAccountProjects= accountProjectsService.save(accountProjectsBean);
			return new ResponseEntity<AccountProjectsEntity>(saveAccountProjects, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Error occurred while saving employee: {}", e.getMessage());
			return new ResponseEntity<AccountProjectsEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAccountProject/{id}")
	public ResponseEntity<AccountProjectsBean> getAccountBeanByEmpId(@PathVariable Integer id) {
	    try {
	        AccountProjectsBean accountProjectsBean = accountProjectsService.getAccountProjectBean(id);
	        
	        if (accountProjectsBean != null) {
	            log.info("AccountProjectsBean retrieved successfully: {}", accountProjectsBean);
	            return ResponseEntity.ok().body(accountProjectsBean);
	        } else {
	            log.warn("AccountProjectsBean not found for id: {}", id);
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        log.error("An error occurred while fetching AccountProjectsBean with id {}: {}", id, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	

	@GetMapping(path = "/getByUuid/{uuid}")
	public ResponseEntity<AccountProjectsBean> getByUUID(@PathVariable String uuid){
	{
		log.info("Entered into getbyuuid controller");
		AccountProjectsBean bean=null;
		try {
			log.info("entered into fingbyuuid service");
		bean = accountProjectsService.findByUuid(uuid);
		log.info("coming out of  fingbyuuid service");
		return new ResponseEntity<AccountProjectsBean>(bean,HttpStatus.FOUND);
		}
		catch(Exception e)
		{
			log.error("Error occurred while saving employee: {}", e.getMessage());
			return new ResponseEntity<AccountProjectsBean>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	}
	}
	@PutMapping("/updateAccountProject")
	public ResponseEntity<AccountProjectsBean> updateAccountProject(@RequestBody AccountProjectsBean accountProjectsBean) {
	    log.info("updateAccountProject in controller start");
	    log.info("accountProjectsBean object: {}", accountProjectsBean);
	    
	    try {
	        AccountProjectsBean updateAccountProject = accountProjectsService.updateAccountProject(accountProjectsBean);
	        log.info("updateAccountProject in controller end");
	        return ResponseEntity.ok().body(updateAccountProject);
	    } catch (IllegalArgumentException e) {
	        log.error("Bad request received while updating AccountProjectsBean: {}", e.getMessage());
	        return ResponseEntity.badRequest().build();
	    } catch (UUIDNotFoundException e) {
	        log.warn("AccountProjectsBean not found: {}", e.getMessage());
	        return ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        log.error("An error occurred while updating AccountProjectsBean: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}



	@GetMapping(path="/getaccount")
	public ResponseEntity<List<AccountBean>> getAccount() {
	    try {
	        List<AccountBean> beans = accountProjectsService.getAccountBean();
	        log.info("Retrieved {} account beans", beans.size());
	        return ResponseEntity.ok().body(beans);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching account beans: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	

	@GetMapping(path="/getEmployee")
	public ResponseEntity<List<EmployeeBean>> getAllEmployees() {
	    try {
	        List<EmployeeBean> beans = accountProjectsService.getEmployeeBean();
	        log.info("Retrieved {} employee beans", beans.size());
	        return ResponseEntity.ok().body(beans);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching employee beans: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

//	
//	@PutMapping("delete/{accountProjectId}")
//	public ResponseEntity<String> deleteProject(@PathVariable Integer accountProjectId){
//		String result=null;
//		result=accountProjectsService.updateDeleteStatus(accountProjectId);
//		return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT );
//	}
	@PutMapping("delete/{accountProjectId}")
	public ResponseEntity<String> deleteProject(@PathVariable Integer accountProjectId) {
	    try {
	        String result = accountProjectsService.updateDeleteStatus(accountProjectId);
	        return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT );
	    } catch (Exception e) {
	        log.error("An error occurred while deleting project with ID {}: {}", accountProjectId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting project.");
	    }
	}




	
}

