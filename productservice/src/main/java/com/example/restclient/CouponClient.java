package com.example.restclient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Coupon;

@FeignClient("zuul-api-gateway")
//@FeignClient("COUPONSERVICE")
@RibbonClient("COUPONSERVICE")
public interface CouponClient {
	
	@RequestMapping(value = "couponservice/couponapi/coupon/{code}" , method = RequestMethod.GET)
	Coupon getCoupon(@PathVariable("code") String code);

}
