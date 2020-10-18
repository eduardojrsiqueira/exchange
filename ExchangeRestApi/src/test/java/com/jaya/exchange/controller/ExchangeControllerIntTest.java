package com.jaya.exchange.controller;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jaya.exchange.entity.CurrencyEnum;
import com.jaya.exchange.entity.ExchangeOperation;

@ContextConfiguration ("classpath*:testContext.xml")
@RunWith (SpringJUnit4ClassRunner.class)
@Transactional
public class ExchangeControllerIntTest {
	
	@Autowired
	private ExchangeController controller;
	
	@Test
	public void testConvert() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Object objectReturn = controller.findByUserId("AAAA");
		Assert.assertTrue(objectReturn instanceof ResponseEntity);
		ResponseEntity<List<ExchangeOperation>> response = (ResponseEntity<List<ExchangeOperation>>) objectReturn;
		Assert.assertTrue(response.getBody().isEmpty());
		ExchangeOperation json = new ExchangeOperation();
		json.setCurrencySource(CurrencyEnum.BRL);
		json.setCurrencyTarget(CurrencyEnum.EUR);
		json.setValueSource(BigDecimal.valueOf(1000));
		json.setUserId("AAAA");
		controller.convert(json);
		objectReturn = controller.findByUserId("AAAA");
		Assert.assertEquals(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getStatusCode(), HttpStatus.OK);
		Assert.assertFalse(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().isEmpty());
		Assert.assertEquals(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().get(0).getUserId(), json.getUserId());
		Assert.assertEquals(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().get(0).getCurrencySource(), json.getCurrencySource());
		Assert.assertEquals(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().get(0).getCurrencyTarget(), json.getCurrencyTarget());
		Assert.assertEquals(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().get(0).getValueSource(), json.getValueSource());
		Assert.assertNotNull(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().get(0).getId());
	}
	
	@Test
	public void testConvertFail() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ExchangeOperation json = new ExchangeOperation();
		json.setCurrencySource(CurrencyEnum.BRL);
		json.setCurrencyTarget(CurrencyEnum.EUR);
		json.setValueSource(BigDecimal.valueOf(1000));
		Object objectReturn = controller.convert(json);
		Assert.assertTrue(objectReturn instanceof ResponseEntity);
		Assert.assertEquals(((ResponseEntity<String>)objectReturn).getBody(),"User Id cannot be null.");
		Assert.assertEquals(((ResponseEntity<String>)objectReturn).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testConvertFailCurrency() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ExchangeOperation json = new ExchangeOperation();
		json.setUserId("AAAA");
		json.setCurrencyTarget(CurrencyEnum.EUR);
		json.setValueSource(BigDecimal.valueOf(1000));
		Object objectReturn = controller.convert(json);
		Assert.assertTrue(objectReturn instanceof ResponseEntity);
		Assert.assertEquals(((ResponseEntity<String>)objectReturn).getBody(),"To perform the service, the source currency, the target currency and the amount to be converted must be provided.");
		Assert.assertEquals(((ResponseEntity<String>)objectReturn).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testFindByUserId() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ExchangeOperation json = new ExchangeOperation();
		json.setCurrencySource(CurrencyEnum.BRL);
		json.setCurrencyTarget(CurrencyEnum.EUR);
		json.setValueSource(BigDecimal.valueOf(1000));
		json.setUserId("AAAA");
		controller.convert(json);
		Object objectReturn = controller.findByUserId("AAAA");
		Assert.assertFalse(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().isEmpty());
		objectReturn = controller.findByUserId("AAAC");
		Assert.assertTrue(((ResponseEntity<List<ExchangeOperation>>)objectReturn).getBody().isEmpty());
	}

}
