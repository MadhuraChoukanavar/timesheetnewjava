package com.feuji.employeeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.entity.EmployeeEntity;
import com.feuji.employeeservice.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/save")
	public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody EmployeeBean employeeBean) {
		try {
			log.info("Saving Employee started: {}", employeeBean);
			EmployeeEntity saveEmployeeEntity = employeeService.saveEmployee(employeeBean);
			return new ResponseEntity<>(saveEmployeeEntity, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Error occurred while saving employee: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getReportingMngIdByEmpId/{id}")
    public ResponseEntity<EmployeeBean> getReportingMngIdByEmpId(@PathVariable Integer id) {
        log.info("get reporting manager id by emp id: {}", id);
        EmployeeBean employeeBean = employeeService.getReportingMngIdByEmpId(id);
        if (employeeBean.getReportingManagerId() != null) {
            log.info("reporting manager id: {}", employeeBean);
            return new ResponseEntity<>(employeeBean, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/{userEmpId}")
    public List<EmployeeDto> getEmployeeByUserEmpId(@PathVariable("userEmpId") Integer userEmpId) {
		List<EmployeeDto> employee = employeeService.getByUserEmpId(userEmpId);
		return employee;
    }
	
	 @GetMapping("/gender/{referenceTypeId}")
	    public List<AddEmployee> getGender(@PathVariable Integer referenceTypeId) {
	        return employeeService.getGender(referenceTypeId);
	    }
	 
	 
	 @GetMapping("/EmploymentType/{referenceTypeId}")
	    public List<AddEmployee> getEmploymentType(@PathVariable Integer referenceTypeId) {
	        return employeeService.getEmploymentType(referenceTypeId);
	    }

	 
	 @GetMapping("/referenceTypeId/{referenceTypeId}")
	    public List<AddEmployee> getByReferenceTypeId(@PathVariable Integer referenceTypeId) {
	        return employeeService.getByReferenceTypeId(referenceTypeId);
	    }
//	 
//	 @GetMapping("/Designation/{referenceTypeId}")
//	    public List<AddEmployee> getDesignation(@PathVariable Integer referenceTypeId) {
//	        return employeeService.getEmploymentType(referenceTypeId);
//	    }
//	 
//	 @GetMapping("/BusinessUnit/{referenceTypeId}")
//	    public List<AddEmployee> getBusinessUnit(@PathVariable Integer referenceTypeId) {
//	        return employeeService.getEmploymentType(referenceTypeId);
//	    }
//	@GetMapping("/getByEmail/{email}")
//	public ResponseEntity<EmployeeBean> getByEmail(@PathVariable String email)
//	{
//		log.info(email+" email id");
//		EmployeeBean bean =employeeService.findByEmail(email);
//		return new ResponseEntity<EmployeeBean>(bean,HttpStatus.OK);
//	}
}