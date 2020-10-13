package com.example.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.model.Billing;
import com.example.model.Item;
import com.example.model.Price;
import com.example.repository.BillingRepository;

@RestController
@RequestMapping("/billingapi")
public class BillingController {

	@Autowired
	private BillingRepository billingrepo;

	@Value("${priceapi.services.url}")
	private String priceurl;
	
//	priceapi.services.url=http://localhost:8070/priceapi/prices/
//	taxapi.gettax.url=http://localhost:8080/taxapi/taxpercentage?amount=

	@Value("${taxapi.gettax.url}")
	private String taxurl;
	
	private List<Price> prices;
	
	@PostConstruct
	private void getAllPrices() {	
		//Using rest template to get the price details
		RestTemplate rest = new RestTemplate();
		this.prices = Arrays.asList(rest.getForObject(priceurl, Price[].class)); 
	}
	
	private Set<Item> getPricesByItems(Set<Item> item) {
		item.parallelStream().forEach(itm -> {
			for(Price price : prices) {
				String str1 = itm.getCode().split(" ")[1];
				String str2 = price.getCode().split(" ")[1];
				if(str1.equals(str2)) {
					itm.setPrice(price.getAmount());
				}
			}
		});
		return item;
	}

	
	@RequestMapping(value = "/createbilling", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Billing createbilling(@RequestBody Billing billing) {
		Set<Item> itemlist = getPricesByItems(billing.getItems());
		BigDecimal sum = new BigDecimal(0L);
		for (Item item : itemlist) {
			sum = sum.add(item.getPrice().multiply(item.getQuantity()));
		}
		sum = getTaxPercentage(sum);		
		billing.setTotal(sum);
		associateBillingItems(billing);
		return billingrepo.save(billing);
	}

	private BigDecimal getTaxPercentage(BigDecimal sum) {
		RestTemplate rest = new RestTemplate();
		String url = taxurl + (sum.longValue());
		Long tax = rest.getForObject(url, Long.class); 
		BigDecimal val = sum.multiply(new BigDecimal(tax)).divide(new BigDecimal(100));
		sum = sum.add(val);
		return sum;
	}

	private void associateBillingItems(Billing billing) {
		for (Item item : billing.getItems()) {
			item.setBillingid(billing);
		}
		billing.setItems(billing.getItems());
	}

	@RequestMapping(value = "/billing/{code}", method = RequestMethod.GET)
	public Billing getbilling(@PathVariable("code") Long id) {
		return billingrepo.findById(id).get();
	}

}
