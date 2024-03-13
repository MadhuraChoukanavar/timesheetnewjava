package com.feuji.accountservice.controller;

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

import com.feuji.accountservice.bean.AccountBean;
import com.feuji.accountservice.bean.EmployeeBean;
import com.feuji.accountservice.dto.AccountDTO;
import com.feuji.accountservice.dto.UpdateAccountDto;
import com.feuji.accountservice.entity.AccountEntity;
import com.feuji.accountservice.exception.UUIDNotFoundException;
import com.feuji.accountservice.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/accountSave")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
	private static Logger log = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private AccountService accountService;

	@PostMapping("/save")
	public ResponseEntity<AccountEntity> save(@RequestBody AccountBean accountBean) {
		
		
		try {
			log.info("Saving Account  {}", accountBean);
			AccountEntity saveAccountEntity = accountService.saveAccount(accountBean);

			ResponseEntity<AccountEntity> responseEntity = new ResponseEntity<>(saveAccountEntity, HttpStatus.CREATED);
			System.out.println("Data inserted");
			return responseEntity;
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	
	@GetMapping(path = "/getbyuuid/{uuId}")
	public ResponseEntity<AccountEntity>findByUUId(@PathVariable  String uuId) {
		log.info("Fetching department_details {}",uuId );

		AccountEntity accountEntities = accountService.findByUUId(uuId);

		ResponseEntity<AccountEntity> responseEntity = new ResponseEntity<>(accountEntities,
				HttpStatus.OK);
		return responseEntity;
	}
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(path="/getEmployee")
	public ResponseEntity<List<EmployeeBean>> getAllEmployees() {
	    List<EmployeeBean> beans = accountService.getEmployeeBean();
	    return new ResponseEntity<>(beans, HttpStatus.OK);
	}
	
	@GetMapping(path="/getAccountDto")
	public ResponseEntity<List<AccountDTO>> accountProjectDto(){
		List<AccountDTO> accountDto=accountService.accountDto();
		return new ResponseEntity<>(accountDto,HttpStatus.OK);
	}
	
	 @GetMapping(path = "/fetchByuuId/{uuId}")
		public ResponseEntity<List< UpdateAccountDto>> fetchByUuID(@PathVariable String uuId)
		{
//		 accountSave/fetchByuuId/{uuId}
		 List< UpdateAccountDto> updateDta= accountService.fetchByUuID(uuId);
			log.info("Fetching updateDta {}", updateDta);
			ResponseEntity<List< UpdateAccountDto>>  timeSheetHistory1= new ResponseEntity<List< UpdateAccountDto>>(HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(updateDta);	

		}
	 @PutMapping("/updateAccount")
		public ResponseEntity<AccountEntity> updateAccountProject(@RequestBody AccountBean accountBean) {
		    log.info("updateAccount in controller start");
		    log.info("accountBean object: {}", accountBean);
		    
		    try {
		        AccountEntity updateAccount = accountService.updateAccount(accountBean);
		        log.info("updateAccountProject in controller end");
		        return new ResponseEntity<AccountEntity>(updateAccount, HttpStatus.OK);
		    } catch (IllegalArgumentException e) {
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    } catch (UUIDNotFoundException e) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    } 
		}	

}

