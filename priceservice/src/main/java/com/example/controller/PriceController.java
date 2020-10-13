package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Price;
import com.example.repository.PriceRepository;

@RestController
@RequestMapping("/priceapi")
public class PriceController {

	@Autowired
	private PriceRepository pricerepo;
	
	@RequestMapping(value = "/createprice" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Price> createprice(@RequestBody List<Price> price) {
		return pricerepo.saveAll(price);
	}
	
	///priceapi/price/{code}
	@RequestMapping(value = "/price/{code}" , method = RequestMethod.GET)
	public Price getprice(@PathVariable("code") String code) {
		return pricerepo.findByCode(code);		
	}
	
	@RequestMapping(value = "/prices" , method = RequestMethod.GET)
	public List<Price> getAllPrices() {
		return pricerepo.findAll();	
	}
	
	
}
