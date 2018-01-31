package com.jpmc.supersimplestock.service;

import java.time.LocalDateTime;

import com.jpmc.supersimplestock.model.Trade;

public interface TradeService {

	/**
	 * this method evaluate volume weighted average stock price 
	 * after fetching trades in last specified mins
	 */
	public double calculateVolumeWeightedStockPriceInTimeRange();
	

	/**
	 * @param trade
	 * this method add a stock trade in the memory
	 */
	public void addStockTrade(Trade trade);
}
