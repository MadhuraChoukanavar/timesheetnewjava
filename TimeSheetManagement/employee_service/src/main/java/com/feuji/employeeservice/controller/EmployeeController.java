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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.entity.CommonReferenceTypeEntity;
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
	
	@GetMapping("/reporting-managers")
    public List<AddEmployee> getAllReportingManagers() {
        try {
            log.info("Fetching all reporting managers...");
            List<AddEmployee> reportingManagers = employeeService.getAllReportingManager();
            log.info("Successfully fetched all reporting managers.");
            return reportingManagers;
        } catch (Exception e) {
            log.error("Error occurred while fetching reporting managers: {}", e.getMessage());
            // You can handle the exception as per your requirement, such as returning a custom error response.
//            throw new RuntimeException("Failed to fetch reporting managers. Please try again later.");
        }
		return null;
    }	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees(){
		List<EmployeeEntity> accountEntities=employeeService.getAllEmployees();
		log.info("Fetching employee details {}", accountEntities);
		ResponseEntity<List<EmployeeEntity>> responseEntity = new ResponseEntity<List<EmployeeEntity>>(accountEntities,
				HttpStatus.OK);
		return responseEntity;
	}


	@GetMapping("/referenceTypeId/{referenceTypeId}")
    public List<SaveEmployeeDto> getEmployeesByReferenceTypeId(@PathVariable Integer referenceTypeId) {
        return employeeService.getByReferenceTypeId(referenceTypeId);
    }
	
	@GetMapping("/search")
    public ResponseEntity<List<EmployeeEntity>> searchEmployees(@RequestParam("firstName") String firstName) {
        log.info("Searching employees by first name: {}", firstName);
        List<EmployeeEntity> employees = employeeService.searchEmployeesByFirstName(firstName);
        log.info("Found {} employees matching the search criteria", employees.size());
        return ResponseEntity.ok(employees);
    }
}