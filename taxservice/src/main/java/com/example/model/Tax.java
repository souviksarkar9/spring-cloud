package com.example.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tax {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal minamount;
	private BigDecimal maxamount;
	private BigDecimal taxpercentage;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getMinamount() {
		return minamount;
	}
	public void setMinamount(BigDecimal minamount) {
		this.minamount = minamount;
	}
	public BigDecimal getMaxamount() {
		return maxamount;
	}
	public void setMaxamount(BigDecimal maxamount) {
		this.maxamount = maxamount;
	}
	public BigDecimal getTaxpercentage() {
		return taxpercentage;
	}
	public void setTaxpercentage(BigDecimal taxpercentage) {
		this.taxpercentage = taxpercentage;
	}
	@Override
	public String toString() {
		return "Tax [id=" + id + ", minamount=" + minamount + ", maxamount=" + maxamount + ", taxpercentage="
				+ taxpercentage + "]";
	}


	
	
}
