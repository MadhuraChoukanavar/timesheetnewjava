package com.feuji.skillgapservice.serviceImplementation;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GenerateUUID {
	public String generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
