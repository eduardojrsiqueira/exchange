package com.jaya.exchange.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaya.exchange.entity.ExchangeOperation;
import com.jaya.exchange.exception.ExchangeException;
import com.jaya.exchange.service.ExchangeService;

@RestController
public class ExchangeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeController.class);
	
	@Autowired
	private ExchangeService service;	

	@RequestMapping(value = "/exchange/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findByUserId(@PathVariable("userId") String userId) {
        try {
        	LOGGER.info("Get Endpoint /exchange/{userId} called receiving as parameter '"+userId+"'");
        	List<ExchangeOperation> list = service.findByUserId(userId);
        	LOGGER.info("Get Endpoint /exchange/{userId} - SUCCESS");
			return new ResponseEntity<List<ExchangeOperation>>(list , HttpStatus.OK);
		} catch (ExchangeException e) {
			LOGGER.error("Get Endpoint /exchange/{userId} getting error when receives '"+userId+"' as parameter.");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
	
	@RequestMapping(value = "/exchange", method = RequestMethod.POST)
    @ResponseBody
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Object convert(@RequestBody(required = true) ExchangeOperation param) {
        try {
        	LOGGER.info("Post Endpoint /exchange called.");
        	ExchangeOperation saved = service.convertMoney(param);
        	LOGGER.info("Post Endpoint /exchange - SUCCESS");
			return new ResponseEntity<ExchangeOperation>(saved, HttpStatus.OK);
		} catch (ExchangeException e) {
			LOGGER.error("Post Endpoint /exchange getting error.");
			LOGGER.error(e.getMessage(),e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
	
}
