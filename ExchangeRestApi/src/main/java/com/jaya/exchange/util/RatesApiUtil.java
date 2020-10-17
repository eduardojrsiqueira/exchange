package com.jaya.exchange.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaya.exchange.entity.CurrencyEnum;
import com.jaya.exchange.exception.ExchangeException;

public class RatesApiUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RatesApiUtil.class);
	
	public static BigDecimal callExchangeApi(CurrencyEnum from, CurrencyEnum to) throws IOException, JSONException {
		URL url = new URL("https://api.exchangeratesapi.io/latest?base="+from.getId());
		LOGGER.info("Calling API "+url.getPath());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
        	LOGGER.error("Response code was "+con.getResponseCode()+" with message: "+con.getResponseMessage());
            throw new ExchangeException(con.getResponseMessage());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());
        JSONObject jsonObjectRates = json.getJSONObject("rates");
        LOGGER.info("API "+url.getPath()+" successfully executed.");
        BigDecimal valueReturned = new BigDecimal(jsonObjectRates.getDouble(to.getId()));
        LOGGER.info("API "+url.getPath()+" returned "+valueReturned);
        return valueReturned;
	}
	
}
