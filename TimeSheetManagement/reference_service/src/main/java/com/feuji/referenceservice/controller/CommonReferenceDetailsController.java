package com.feuji.referenceservice.controller;

import java.util.List;

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

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.ReferenceDetailsBean;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.repository.CommonReferenceTypeRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;
import com.feuji.referenceservice.service.CommonReferenceType;
import com.feuji.referenceservice.serviceImpl.CommonReferenceTypeImpl;

@RestController
@RequestMapping("/referencedetails")
@CrossOrigin(origins = "http://localhost:4200")
public class CommonReferenceDetailsController {
	private static Logger log = LoggerFactory.getLogger(CommonReferenceTypeImpl.class);

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	CommonReferenceDetails commonReferenceDetails;

	@GetMapping("/getref/{typeName}")
	 @CrossOrigin(origins = "http://localhost:4200")
	
	public ResponseEntity<List<ReferenceDetailsBean>> getReferenceTypeByName(@PathVariable String typeName)
	{
	try{
		log.info(typeName);
		
		
		List<ReferenceDetailsBean> getbyreferenceType = commonReferenceDetails.getDetailsByTypeId(typeName);
		log.info(getbyreferenceType+"output");
		return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK) ;	
	}
	catch (Exception e) {
    return null;
	}
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<CommonReferenceDetailsBean> getrefenceDetailsById(@PathVariable Integer id)
	{
	try{
		log.info(id+"id");
		
		
		 CommonReferenceDetailsBean referenceById = commonReferenceDetails.getReferenceById(id);
		log.info(referenceById+"output");
		return new ResponseEntity<>(referenceById, HttpStatus.OK) ;	
	}
	catch (Exception e) {
    return null;
	}
	}

}
