package com.jaya.exchange.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaya.exchange.dao.ExchangeDao;
import com.jaya.exchange.entity.ExchangeOperation;
import com.jaya.exchange.exception.ExchangeException;
import com.jaya.exchange.util.RatesApiUtil;

@Service
public class ExchangeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeService.class);
	
	@Autowired
	private ExchangeDao exchangeDao;
	
	public List<ExchangeOperation> findByUserId(String userId){
		if(userId == null || "".equals(userId.trim())) {
			throw new ExchangeException("User Id cannot be null.");
		}
		List<ExchangeOperation> list = exchangeDao.findByUserId(userId);
		LOGGER.info("Method findByUserId found "+list.size()+" operations.");
		return list;
	}
	
	@Transactional
	public ExchangeOperation convertMoney(ExchangeOperation e) {
		try {
			validate(e);
			BigDecimal tax = RatesApiUtil.callExchangeApi(e.getCurrencySource(), e.getCurrencyTarget());
			e.setRate(tax);
			e.setValueTarget(tax.multiply(e.getValueSource()));
			e.setDateTimeOperation(LocalDateTime.now());
			return exchangeDao.insert(e);
		} catch (IOException | JSONException e1) {
			throw new ExchangeException("Something wrong happend when the api.exchangeratesapi.io was called.");
		}
	}
	
	private void validate(ExchangeOperation e) {
		if(e.getUserId() == null || "".equals(e.getUserId().trim())) {
			throw new ExchangeException("User Id cannot be null.");
		}
		if(e.getCurrencySource() == null || e.getValueSource() == null ||e.getCurrencyTarget() == null) {
			throw new ExchangeException("To perform the service, the source currency, the target currency and the amount to be converted must be provided.");
		}
	}

}
