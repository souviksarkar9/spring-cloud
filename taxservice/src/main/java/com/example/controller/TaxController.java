package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Tax;
import com.example.repository.TaxRepository;

@RestController
@RequestMapping("/taxapi")
public class TaxController {

	@Autowired
	private TaxRepository taxrepo;
	
	@RequestMapping(value = "/createtax" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Tax> createtax(@RequestBody List<Tax> tax) {
		return taxrepo.saveAll(tax);		
	}
	
	@RequestMapping(value = "/taxpercentage" , method = RequestMethod.GET)
	public Long gettaxpercentage(@RequestParam("amount") Long amount) {
		 List<Tax> taxlist = taxrepo.findAll();	
		 Long taxpercentage = null;
		 for(Tax tax : taxlist) {
			 if(amount >= tax.getMinamount().longValue()  &&  amount <= tax.getMaxamount().longValue()) {
				 taxpercentage = tax.getTaxpercentage().longValue();
			 }else if(amount > tax.getMaxamount().longValue()) {
				 taxpercentage = tax.getTaxpercentage().longValue();
			 }
		 }
		 return taxpercentage;
	}
	
	
}
