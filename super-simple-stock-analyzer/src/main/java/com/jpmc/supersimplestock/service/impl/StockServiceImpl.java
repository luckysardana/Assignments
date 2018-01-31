package com.jpmc.supersimplestock.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import com.jpmc.supersimplestock.dao.StockData;
import com.jpmc.supersimplestock.exception.InvalidValueException;
import com.jpmc.supersimplestock.model.Stock;
import com.jpmc.supersimplestock.model.StockType;
import com.jpmc.supersimplestock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);
	private final StockData stockData;
	
	@Autowired
	public StockServiceImpl(final StockData stockData) {
		this.stockData = stockData;
	}
	
	/**
	 * this method evaluates dividend yield for a stock and its market price
	 */
	@Override
	public double calculateDividendYield(Stock stock, double marketPrice) {
		validateStockDetails(ImmutableList.of(stock), ImmutableList.of(marketPrice));
		LOGGER.info("Evaluating divident yield for {} & market Price {}", stock, marketPrice);
		
		final double dividendYield;
		if(stock.getType().equals(StockType.COMMON)) 
			dividendYield = stock.getLastDividend() / marketPrice;
		else 
			dividendYield = (stock.getFixedDividend() * stock.getParValue()) / marketPrice;

		return dividendYield;
	}
	
	/**
	 * this method evaluates P/E ratio for a market price and dividend value
	 */
	
	@Override
	public double calculatePriceToEarningRatio(double marketPrice, double dividendYield) {
	
		validateStockDetails(ImmutableList.of(), ImmutableList.of(marketPrice));
		double priceToEarningRation = 0.0;
		LOGGER.info("Evaluating PriceToEarningRation for a stock with dividend: {} and marketPrice: {} ", dividendYield, marketPrice);
		if(dividendYield > 0.0) {
			priceToEarningRation = marketPrice / dividendYield; 
		}
		return priceToEarningRation;
	}
	
	/**
	 * this method evaluates all share index for all the stocks 
	 */
	@Override
	public Double allShareIndex(Map<String, Stock> stocks) {
		validateStockDetails(ImmutableList.copyOf(stocks.values()), ImmutableList.of());
		
		LOGGER.info("Evaluating all share index for Stocks Provided in test data of GBCE & market Price assumed ");
		final Double allShareIndex;
		allShareIndex = stocks.values().stream()
				.mapToDouble(stock -> stockData.findMarketPriceByStockSymbol(stock.getStockSymbol())).sum();
		return Math.pow(allShareIndex, 1.0 / stocks.size());
	}
	

	/**
	 * this method validate basic stock details like Par value, Last Dividend, Fixed Dividend
	 * & for stock market price
	 */
	private void validateStockDetails(List<Stock> stocks, List<Double> stockMarketPrices) {
		
		LOGGER.info("Validating Stock Details ");
		
		stocks.forEach((stock -> {
			if(stock.getParValue() <= 0.0) {
				throw new InvalidValueException("Invalid value of Par Value");
			}
			
			if(stock.getLastDividend() < 0.0) {
				throw new InvalidValueException("Invalid value of Last Dividend");
			}
			
			if(stock.getFixedDividend() < 0.0) {
				throw new InvalidValueException("Invalid value of Fixed Dividend");
				
			}
			
		}));
		
		stockMarketPrices.forEach(( price ->{
			if(price <= 0.0) {
				throw new InvalidValueException("Invalid value of Market Price");
			}
		}));
	}
}

