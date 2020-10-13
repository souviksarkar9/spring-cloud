package com.example.controller;

import java.math.BigDecimal;
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
import com.example.restclients.PriceClient;
import com.example.restclients.TaxClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/billingapi")
public class BillingController {

	@Autowired
	private BillingRepository billingrepo;

	@Autowired
	PriceClient priceClient;
	
	@Autowired
	TaxClient taxClient;
	
	private List<Price> prices;
	
	@PostConstruct
	private void getAllPrices() {	
		this.prices = priceClient.getPrices();
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
	@HystrixCommand(fallbackMethod = "createBillingSendErrorReport")
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
	
	public Billing createBillingSendErrorReport( Billing billing) {
		return billing;
	}

	
	private BigDecimal getTaxPercentage(BigDecimal sum) {
		Long tax = taxClient.gettaxpercentage(sum.longValue());
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
