package com.jpmc.supersimplestock.service;

import java.util.Map;

import com.jpmc.supersimplestock.model.Stock;

public interface StockService {

	
	public double calculateDividendYield(Stock stock, double marketPrice);
	public double calculatePriceToEarningRatio(double marketPrice, double dividendYield);
	public Double allShareIndex(Map<String, Stock> stocks);
}
