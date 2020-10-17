package com.jaya.exchange.entity;

public enum CurrencyEnum {

	BRL("BRL"), USD("USD"), EUR("EUR"), JPY("JPY");
	
	private String id;
	
	private CurrencyEnum(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
