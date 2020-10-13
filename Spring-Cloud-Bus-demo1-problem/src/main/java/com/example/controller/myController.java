package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class myController {
	
	@Value("${com.myvar}")
	private String var; 
	
	@RequestMapping(value = "/prop" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProp() {
		return var;
	}
	

}
