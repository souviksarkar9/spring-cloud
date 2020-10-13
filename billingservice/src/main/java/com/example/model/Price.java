package com.example.model;

import java.math.BigDecimal;

public class Price {
	
	private Long id;
	private String code;
	private BigDecimal amount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Price [id=" + id + ", code=" + code + ", amount=" + amount + "]";
	}
	
	
	
}
