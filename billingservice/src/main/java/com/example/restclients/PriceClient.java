package com.example.restclients;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Price;

//@FeignClient("PRICESERVICE")
@FeignClient("zuul-api-gateway")
@RibbonClient("PRICESERVICE")
public interface PriceClient {
	
	@RequestMapping(value="priceservice/priceapi/prices/", method = RequestMethod.GET)
	public List<Price> getPrices();

}
