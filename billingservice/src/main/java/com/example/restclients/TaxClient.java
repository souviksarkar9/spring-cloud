package com.example.restclients;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("zuul-api-gateway")
//@FeignClient("TAXSERVICE")
@RibbonClient("TAXSERVICE")
public interface TaxClient {
	
	@RequestMapping(value="taxservice/taxapi/taxpercentage/", method = RequestMethod.GET)
	public Long gettaxpercentage(@RequestParam("amount") Long amount);

}
