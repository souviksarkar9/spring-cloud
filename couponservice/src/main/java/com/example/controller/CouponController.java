package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Coupon;
import com.example.repository.CouponRepository;

@RestController
@RequestMapping("/couponapi")
public class CouponController {

	@Autowired
	private CouponRepository couponrepo;
	
	@RequestMapping(value = "/createcoupon" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	public Coupon createCoupon(@RequestBody Coupon coupon) {
		return couponrepo.save(coupon);		
	}
	
	///couponapi/coupon/{code}
	@RequestMapping(value = "/coupon/{code}" , method = RequestMethod.GET)
	public Coupon getCoupon(@PathVariable("code") String code) {
		return couponrepo.findByCode(code);		
	}
	
	
}
