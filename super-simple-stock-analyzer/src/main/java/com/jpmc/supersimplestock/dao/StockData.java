package com.jpmc.supersimplestock.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.jpmc.supersimplestock.model.Stock;
import com.jpmc.supersimplestock.model.StockType;

@Repository
public class StockData {
	public Map<String, Stock> getSymbolAndStockMap() {

		return ImmutableMap.of("TEA", new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0),
				"POP", new Stock("POP", StockType.COMMON, 8.0, 0.0, 100.0), 
				"ALE", new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0), 
				"GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 0.2, 100.0), 
				"JOE", new Stock("JOE", StockType.COMMON, 13.0, 0.0, 250.0));

	}
	
	/*
	 * This method returns the map of Stock symbol and current market price of the stock
	 */
	public Map<String, Double> getStockMarketPriceMap() {
		return ImmutableMap.of("TEA", 100.10, 
				"POP", 99.10,
				"ALE", 62.10,
				"GIN", 101.10,
				"JOE", 255.10);
	}
	
	public Stock findByStockSymbol(String stockSymbol) {
		return getSymbolAndStockMap().get(stockSymbol);
	}
	public Double findMarketPriceByStockSymbol(String stockSymbol) {
		return getStockMarketPriceMap().get(stockSymbol);
	}
	
	public Stock getAnInvalidStock() {
		return new Stock("POP", StockType.COMMON, -8.0, -0.0, 100.0);
	}
	
	
}
