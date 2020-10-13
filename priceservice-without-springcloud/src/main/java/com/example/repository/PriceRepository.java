package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long>{

	Price findByCode(String code);

}
