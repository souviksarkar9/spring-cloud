package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Coupon;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.restclient.CouponClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/productapi")
public class ProductController {

	@Autowired
	private ProductRepository productrepo;
	
	@Autowired
	CouponClient couponclient;
	
	@HystrixCommand(fallbackMethod = "createproductSendErrorResponse")
	@RequestMapping(value = "/createproduct" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	public Product createproduct(@RequestBody Product product) {
		Coupon coupon = couponclient.getCoupon(product.getCouponcode());
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return productrepo.save(product);		
	}
	
	public Product createproductSendErrorResponse(Product product) {
		return product;
	}
	
	
	@RequestMapping(value = "/product/{id}" , method = RequestMethod.GET)
	public Product getproduct(@PathVariable("id") Long id) {
		return productrepo.findById(id).get();	
	}
	
	@RequestMapping(value = "/products" , method = RequestMethod.GET)
	public List<Product> getproduct() {
		return productrepo.findAll();
	}
	
	
}
