package com.feuji.employeeservice.controller;

import java.util.Collections;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDisplayDto;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.dto.UpadteEmployeeDto;
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
	
//	@GetMapping("/referenceTypeId/{referenceTypeId}")
//    public List<SaveEmployeeDto> getEmployeesByReferenceTypeId(@PathVariable Integer referenceTypeId) {
//        return employeeService.getByReferenceTypeId(referenceTypeId);
//    }
	
	@GetMapping("/referenceTypeId/{referenceTypeId}")
	public ResponseEntity<List<SaveEmployeeDto>> getEmployeesByReferenceTypeId(@PathVariable Integer referenceTypeId) {
	    try {
	        log.info("Fetching employees by reference type ID: {}", referenceTypeId);
	        List<SaveEmployeeDto> employees = employeeService.getByReferenceTypeId(referenceTypeId);
	        log.info("Retrieved {} employee(s) by reference type ID: {}", employees.size(), referenceTypeId);
	        return ResponseEntity.ok(employees);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching employees by reference type ID {}: {}", referenceTypeId, e.getMessage());
	        // You may choose to handle the exception differently based on your requirement.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

//	@GetMapping("/getReportingMngIdByEmpId/{id}")
//    public ResponseEntity<EmployeeBean> getReportingMngIdByEmpId(@PathVariable Integer id) {
//        log.info("get reporting manager id by emp id: {}", id);
//        EmployeeBean employeeBean = employeeService.getReportingMngIdByEmpId(id);
//        if (employeeBean.getReportingManagerId() != null) {
//            log.info("reporting manager id: {}", employeeBean);
//            return new ResponseEntity<>(employeeBean, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
	
	@GetMapping("/getReportingMngIdByEmpId/{id}")
	public ResponseEntity<EmployeeBean> getReportingMngIdByEmpId(@PathVariable Integer id) {
	    try {
	        log.info("Fetching reporting manager id by employee ID: {}", id);
	        EmployeeBean employeeBean = employeeService.getReportingMngIdByEmpId(id);
	        
	        if (employeeBean.getReportingManagerId() != null) {
	            log.info("Reporting manager ID found: {}", employeeBean.getReportingManagerId());
	            return ResponseEntity.ok(employeeBean);
	        } else {
	            log.warn("Reporting manager ID not found for employee ID: {}", id);
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        log.error("An error occurred while fetching reporting manager ID for employee ID {}: {}", id, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

//	
//	@GetMapping("/getAll")
//	public ResponseEntity<List<EmployeeEntity>> getAllEmployees(){
//		List<EmployeeEntity> accountEntities=employeeService.getAllEmployees();
//		log.info("Fetching employee details {}", accountEntities);
//		ResponseEntity<List<EmployeeEntity>> responseEntity = new ResponseEntity<List<EmployeeEntity>>(accountEntities,
//				HttpStatus.OK);
//		return responseEntity;
//	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
	    try {
	        log.info("Fetching all employees");
	        List<EmployeeEntity> accountEntities = employeeService.getAllEmployees();
	        log.info("Retrieved {} employee(s)", accountEntities.size());
	        return ResponseEntity.ok(accountEntities);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching all employees: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	
//	@GetMapping("/{userEmpId}")
//    public List<EmployeeDto> getEmployeeByUserEmpId(@PathVariable("userEmpId") Integer userEmpId) {
//		List<EmployeeDto> employee = employeeService.getByUserEmpId(userEmpId);
//		return employee;
//    }
//	
	
	@GetMapping("/{userEmpId}")
	public List<EmployeeDto> getEmployeeByUserEmpId(@PathVariable("userEmpId") Integer userEmpId) {
	    try {
	        log.info("Fetching employee by user employee ID: {}", userEmpId);
	        List<EmployeeDto> employee = employeeService.getByUserEmpId(userEmpId);
	        log.info("Retrieved {} employee(s) by user employee ID: {}", employee.size(), userEmpId);
	        return employee;
	    } catch (Exception e) {
	        log.error("An error occurred while fetching employee by user employee ID {}: {}", userEmpId, e.getMessage());
	        // You may choose to handle the exception differently based on your requirement.
	        return Collections.emptyList(); // Returning an empty list as a fallback.
	    }
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
//	public List<EmployeeDisplayDto> getEmployeeDetails(Integer employeeId)
//	@GetMapping(path = "/getEmployeeDetails")
//	public ResponseEntity<List<EmployeeDisplayDto>> getEmployeeDetails()
//	{
//
//		List<EmployeeDisplayDto> updateDta= employeeService.getEmployeeDetails();
//		log.info("Fetching updateDta {}", updateDta);
//		ResponseEntity<List<EmployeeDisplayDto>>  timeSheetHistory1= new ResponseEntity<List<EmployeeDisplayDto>>(HttpStatus.OK);
//		return ResponseEntity.status(HttpStatus.OK).body(updateDta);	
//
//	}
	
	
	
	@GetMapping(path = "/getEmployeeDetails")
	public ResponseEntity<List<EmployeeDisplayDto>> getEmployeeDetails() {
	    try {
	        log.info("Fetching employee details");
	        List<EmployeeDisplayDto> updateDta = employeeService.getEmployeeDetails();
	        log.info("Retrieved employee details: {}", updateDta);
	        return ResponseEntity.ok(updateDta);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching employee details: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
//	@GetMapping(path = "/getEmployeeDetailByUUiD/{uuid}")
//	public ResponseEntity<List<UpadteEmployeeDto>> getEmployeeDetailByUUiD(@PathVariable String uuid)
//	{
//
//		List<UpadteEmployeeDto> updateDta= employeeService.getEmployeeDetailByUUiD(uuid);
//		log.info("Fetching updateDta {}", updateDta);
//		ResponseEntity<List<UpadteEmployeeDto>>  timeSheetHistory1= new ResponseEntity<List<UpadteEmployeeDto>>(HttpStatus.OK);
//		return ResponseEntity.status(HttpStatus.OK).body(updateDta);	
//
//	}
	
	
	@GetMapping(path = "/getEmployeeDetailByUUiD/{uuid}")
	public ResponseEntity<List<UpadteEmployeeDto>> getEmployeeDetailByUUiD(@PathVariable String uuid) {
	    try {
	        log.info("Fetching employee details for UUID: {}", uuid);
	        List<UpadteEmployeeDto> updateDta = employeeService.getEmployeeDetailByUUiD(uuid);
	        log.info("Retrieved employee details: {}", updateDta);
	        return ResponseEntity.ok(updateDta);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching employee details for UUID {}: {}", uuid, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

//	@PutMapping("/updateEmployee")
//	public ResponseEntity<EmployeeEntity> updateEmployee(@RequestBody EmployeeBean employeeBean) {
//	    log.info("update  in controller start");
//	    log.info("accountBean object: {}", employeeBean);
//	    
//	    try {
//	    	EmployeeEntity updateEmployee = employeeService.updateEmployee(employeeBean);
//	        log.info("updateAccountProject in controller end");
//	        return new ResponseEntity<EmployeeEntity>(updateEmployee, HttpStatus.OK);
//	    } catch (IllegalArgumentException e) {
//	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	    } catch (Exception e) {
//	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    } 
//	}	
	
	@PutMapping("/updateEmployee")
	public ResponseEntity<EmployeeEntity> updateEmployee(@RequestBody EmployeeBean employeeBean) {
	    try {
	        log.info("Updating employee in controller");
	        log.info("EmployeeBean object: {}", employeeBean);
	        
	        EmployeeEntity updateEmployee = employeeService.updateEmployee(employeeBean);
	        
	        log.info("Employee updated successfully");
	        return ResponseEntity.ok(updateEmployee);
	    } catch (IllegalArgumentException e) {
	        log.error("Bad request received while updating employee: {}", e.getMessage());
	        return ResponseEntity.badRequest().build();
	    } catch (Exception e) {
	        log.error("An error occurred while updating employee: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

	
//	@DeleteMapping("/deleteEmp/{employeeId}")
//	public ResponseEntity<EmployeeEntity> delate(@PathVariable Integer employeeId) {
//		// log.info("holiday delete",holidayId);)
//		EmployeeEntity employeeEntity = employeeService.delete(employeeId);
//		log.info("Delete department_details {}", employeeId);
//		ResponseEntity<EmployeeEntity> responseEntity = new ResponseEntity<EmployeeEntity>(employeeEntity, HttpStatus.OK);
//		return responseEntity;
//
//	}
	
	@DeleteMapping("/deleteEmp/{employeeId}")
	public ResponseEntity<EmployeeEntity> delete(@PathVariable Integer employeeId) {
	    try {
	        log.info("Deleting employee with ID: {}", employeeId);
	        EmployeeEntity employeeEntity = employeeService.delete(employeeId);
	        log.info("Deleted employee with ID: {}", employeeId);
	        return ResponseEntity.ok(employeeEntity);
	    } catch (Exception e) {
	        log.error("An error occurred while deleting employee with ID {}: {}", employeeId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}