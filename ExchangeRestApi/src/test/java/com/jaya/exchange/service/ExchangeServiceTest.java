package com.jaya.exchange.service;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jaya.exchange.dao.ExchangeDao;
import com.jaya.exchange.entity.CurrencyEnum;
import com.jaya.exchange.entity.ExchangeOperation;
import com.jaya.exchange.exception.ExchangeException;
import com.jaya.exchange.util.RatesApiUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RatesApiUtil.class})
public class ExchangeServiceTest {

	@Mock
	private ExchangeDao dao;
	
	@InjectMocks
	private ExchangeService service;
	
	private List<ExchangeOperation> createList(){
		List<ExchangeOperation> list = new ArrayList<ExchangeOperation>();
		list.add(new ExchangeOperation());
		list.add(new ExchangeOperation());
		list.add(new ExchangeOperation());
		return list;
	}
	
	@Test(expected = ExchangeException.class)
	public void findByUserIdMissingIdNull() {
		try {
			service.findByUserId(null);
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("User Id cannot be null.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ExchangeException.class)
	public void findByUserIdMissingIdEmpty() {
		try {
			service.findByUserId(" ");
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("User Id cannot be null.", e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void findByUserIdSuccess() {
		String user = "AAAA";
		PowerMockito.when(dao.findByUserId(user)).thenReturn(this.createList());
		List<ExchangeOperation> list = service.findByUserId(user);
		Assert.assertEquals(3, list.size());
	}
	
	@Test(expected = ExchangeException.class)
	public void convertMoneyExceptionUserIdNull() {
		try {
			ExchangeOperation e = new ExchangeOperation();
			service.convertMoney(e);
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("User Id cannot be null.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ExchangeException.class)
	public void convertMoneyExceptionUserIdEmpty() {
		try {
			ExchangeOperation e = new ExchangeOperation();
			e.setUserId(" ");
			service.convertMoney(e);
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("User Id cannot be null.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ExchangeException.class)
	public void convertMoneyExceptionCurrencySourceNull() {
		try {
			ExchangeOperation e = new ExchangeOperation();
			e.setUserId("AAAA");
			e.setCurrencySource(null);
			e.setCurrencyTarget(CurrencyEnum.EUR);
			e.setValueSource(BigDecimal.TEN);
			service.convertMoney(e);
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("To perform the service, the source currency, the target currency and the amount to be converted must be provided.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ExchangeException.class)
	public void convertMoneyExceptionCurrencyTargetNull() {
		try {
			ExchangeOperation e = new ExchangeOperation();
			e.setUserId("AAAA");
			e.setCurrencySource(CurrencyEnum.EUR);
			e.setCurrencyTarget(null);
			e.setValueSource(BigDecimal.TEN);
			service.convertMoney(e);
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("To perform the service, the source currency, the target currency and the amount to be converted must be provided.", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected = ExchangeException.class)
	public void convertMoneyExceptionValueNull() {
		try {
			ExchangeOperation e = new ExchangeOperation();
			e.setUserId("AAAA");
			e.setCurrencySource(CurrencyEnum.EUR);
			e.setCurrencyTarget(CurrencyEnum.BRL);
			e.setValueSource(null);
			service.convertMoney(e);
			fail();
		}catch(ExchangeException e) {
			Assert.assertEquals("To perform the service, the source currency, the target currency and the amount to be converted must be provided.", e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void convertMoneySuccess() throws IOException, JSONException {
		PowerMockito.mockStatic(RatesApiUtil.class);
		PowerMockito.when(RatesApiUtil.callExchangeApi(CurrencyEnum.EUR, CurrencyEnum.BRL)).thenReturn(BigDecimal.TEN);
		ExchangeOperation e = new ExchangeOperation();
		e.setUserId("AAAA");
		e.setCurrencySource(CurrencyEnum.EUR);
		e.setCurrencyTarget(CurrencyEnum.BRL);
		e.setValueSource(BigDecimal.TEN);
		Mockito.when(dao.insert(Mockito.any())).thenReturn(getCompleteEntity());
		e = service.convertMoney(e);
		Assert.assertEquals(new Long(1), e.getId());
		Assert.assertNotNull(e.getValueTarget());
		Assert.assertNotNull(e.getDateTimeOperation());
		Assert.assertEquals(new BigDecimal(100), e.getValueTarget());
		Assert.assertEquals(BigDecimal.TEN, e.getConvertTax());
	}

	private ExchangeOperation getCompleteEntity() {
		ExchangeOperation e = new ExchangeOperation();
		e.setUserId("AAAA");
		e.setCurrencySource(CurrencyEnum.EUR);
		e.setCurrencyTarget(CurrencyEnum.BRL);
		e.setValueSource(BigDecimal.TEN);
		e.setValueTarget(new BigDecimal(100));
		e.setConvertTax(BigDecimal.TEN);
		e.setDateTimeOperation(LocalDateTime.now());
		e.setId(1L);
		return e;
	}
}
