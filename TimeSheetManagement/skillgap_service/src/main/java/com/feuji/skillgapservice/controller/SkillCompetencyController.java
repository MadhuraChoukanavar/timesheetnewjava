package com.feuji.skillgapservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.service.SkillCompetencyService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class SkillCompetencyController {
	@Autowired
	private SkillCompetencyService skillCompetencyService;
	@PostMapping(path = "/insert")
	public ResponseEntity<SkillCompetencyBean> insertion(@RequestBody SkillCompetencyBean skillCompetencyBean)
	{
		log.info("entered into Controller save");
		
		
		
			log.info("entered into Service save method");
			skillCompetencyService.save(skillCompetencyBean);
			log.info("completed into Service save method");

	    
	   
		log.info("completed into Controller save");

		return new ResponseEntity<SkillCompetencyBean>(skillCompetencyBean,HttpStatus.CREATED);
	}
	@PostMapping(path = "/update/{uuid}")
	public ResponseEntity<SkillCompetencyBean> updateAllFeilds(@RequestBody SkillCompetencyBean competencyBean,@PathVariable String uuid)
	{
		log.info("entered into the update skill competency controller");
			log.info("Enterd into update allrecordsservice method");
		SkillCompetencyBean updateAllRecords = skillCompetencyService.updateAllRecords(competencyBean);
		log.info("Coming out from the allrecordsservice method");
		log.info("completed the update skill competency controller");

		return new ResponseEntity<SkillCompetencyBean>(competencyBean,HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/getByUuid/{uuid}")
	public ResponseEntity<SkillCompetencyBean> getByUUID(@PathVariable String uuid) throws RecordNotFoundException
	{
		log.info("Entered into getbyuuid controller");
		SkillCompetencyBean bean=null;
		try {
			log.info("entered into fingbyuuid service");
		bean = skillCompetencyService.findByUuid(uuid);
		log.info("coming out of  fingbyuuid service");
		}
		catch(Exception e)
		{
			throw new RecordNotFoundException(e.getMessage());
		}
		
		return new ResponseEntity<SkillCompetencyBean>(bean,HttpStatus.FOUND);
	}
}

