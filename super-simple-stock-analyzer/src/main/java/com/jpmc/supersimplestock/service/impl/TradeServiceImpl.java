package com.jpmc.supersimplestock.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.google.common.annotations.VisibleForTesting;
import com.jpmc.supersimplestock.dao.TradeData;
import com.jpmc.supersimplestock.exception.InvalidValueException;
import com.jpmc.supersimplestock.model.Trade;
import com.jpmc.supersimplestock.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TradeServiceImpl implements TradeService {

	//can be made configurable 
    private int stockTradesFetchRangeInMins = 15;
	private final TradeData tradeData;
	
	
	@Autowired
    public TradeServiceImpl(final TradeData tradeData) {
		this.tradeData = tradeData;
    }

	
	private static Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);
	
	/**
	 * this method evaluate volume weighted average stock price of trades happened 
	 * between now and last 15 minutes. This time is configurable in application.properties file
	 */
	@Override
	public double calculateVolumeWeightedStockPriceInTimeRange() {
		
		List<Trade> tradesInLastSpecifiedMins = this.getTradesBetweenDuration(LocalDateTime.now(), stockTradesFetchRangeInMins);
		
		LOGGER.info("Calculating VolumeWeightedStockPrice of trades:{}  InLastSpecifiedMins: {} ", tradesInLastSpecifiedMins, stockTradesFetchRangeInMins);;
		
		int totalQuantity = tradesInLastSpecifiedMins.stream().mapToInt(Trade::getQuantity).sum();
		
		double volumeWeigthedStockPrice = tradesInLastSpecifiedMins.stream()
					.mapToDouble( trade -> trade.getQuantity() * trade.getPrice()).sum();
			
		return volumeWeigthedStockPrice / totalQuantity;
		
	}
	

	@Override
	public void addStockTrade(Trade trade) {
		validateStockTrade(trade);
		LOGGER.info("Adding a Stock Trade {} ", trade);
		new TradeData().addTrade(trade);
	}
	
	public List<Trade> getAllTrades() {
		LOGGER.info("Fetching all trades recorded");
		return tradeData.getAllTrades();
	}
	
	/**
	 * 
	 * @param dateTime
	 * @param durationInMinutes
	 * @return list of trades after filtering between now dateTime and time specified in mins
	 */
	@VisibleForTesting
	List<Trade> getTradesBetweenDuration(LocalDateTime dateTime, long durationInMinutes) {
		
		LocalDateTime initialDateTime = dateTime.minusMinutes(durationInMinutes);
		return getAllTrades().stream().filter(trade -> trade.getLocalDateTime()
				.isAfter(initialDateTime) && trade.getLocalDateTime().equals(dateTime))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * this method validates a stock trade
	 * @param trade
	 */
	private void validateStockTrade(Trade trade) {
		LOGGER.info("Validating a stock trade details");
		if(trade.getStock() == null) {
			throw new InvalidValueException("A valid stock is required for trading");
			
		} 
		if(trade.getQuantity() < 1 ) {
			throw new InvalidValueException("A valid quantity of stock is required for trading");
		}
	}
	
}
