package com.starwar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.starwar.dto.ReturningDTO;
import com.starwar.service.StarWarService;

@RestController
public class StarWarResources {
	
	@Autowired
	StarWarService service;

	@GetMapping("/api")
	public ReturningDTO getAllResource(@RequestParam String type, @RequestParam(required = false) String name) {
		
		
		return service.getAllTypes(type, name);
		
		
	}
	
	@GetMapping()
	public String test() {
		return "hello";
	}
}
