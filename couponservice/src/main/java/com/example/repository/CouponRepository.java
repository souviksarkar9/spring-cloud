package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{

	Coupon findByCode(String code);

}
