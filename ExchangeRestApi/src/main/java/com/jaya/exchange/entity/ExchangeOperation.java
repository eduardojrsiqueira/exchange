package com.jaya.exchange.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EXCHANGE_OPERATION")
public class ExchangeOperation implements Serializable {
	
	private static final long serialVersionUID = 1037107740808819157L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "CURRENCY_SOURCE")
	private CurrencyEnum currencySource;
	
	@Column(name = "VALUE_SOURCE")
	private BigDecimal valueSource;
	
	@Column(name = "CURRENCY_TARGET")
	private CurrencyEnum currencyTarget;
	
	@Column(name = "VALUE_TARGET")
	private BigDecimal valueTarget;
	
	@Column(name = "EXCHANGE_RATE")
	private BigDecimal rate;
	
	@Column(name = "DATE_TIME_OPERATION")
	private LocalDateTime dateTimeOperation;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public LocalDateTime getDateTimeOperation() {
		return dateTimeOperation;
	}

	public void setDateTimeOperation(LocalDateTime dateTimeOperation) {
		this.dateTimeOperation = dateTimeOperation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CurrencyEnum getCurrencySource() {
		return currencySource;
	}

	public void setCurrencySource(CurrencyEnum currencySource) {
		this.currencySource = currencySource;
	}

	public BigDecimal getValueSource() {
		return valueSource;
	}

	public void setValueSource(BigDecimal valueSource) {
		this.valueSource = valueSource;
	}

	public CurrencyEnum getCurrencyTarget() {
		return currencyTarget;
	}

	public void setCurrencyTarget(CurrencyEnum currencyTarget) {
		this.currencyTarget = currencyTarget;
	}

	public BigDecimal getValueTarget() {
		return valueTarget;
	}

	public void setValueTarget(BigDecimal valueTarget) {
		this.valueTarget = valueTarget;
	}
	
}
