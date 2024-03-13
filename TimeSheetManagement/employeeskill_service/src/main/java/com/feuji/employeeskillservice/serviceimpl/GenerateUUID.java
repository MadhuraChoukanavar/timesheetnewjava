package com.feuji.employeeskillservice.serviceimpl;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GenerateUUID {

	public String generateUniqueId() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString();
	}
}
