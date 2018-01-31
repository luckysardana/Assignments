package com.jpmc.supersimplestock.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.jpmc.supersimplestock.model.Trade;
import com.jpmc.supersimplestock.model.TradeType;
import org.springframework.stereotype.Repository;


@Repository
public class TradeData {

	
	private List<Trade> trades;

	/**
	 * This method saves a trade in the memory using ArrayList
	 */
	
	public void addTrade(Trade trade) {
		if(trades == null) {
			trades = new ArrayList<>();
		}
		trades.add(trade);
	}

	/**
	 * This method retrieve all trades in the memory using ArrayList
	 */
	
	public List<Trade> getAllTrades() {
		return trades;
	}
	
	
	/**
	 * This method retrieve all trades from the memory for a particular stock
	 */
	
	public List<Trade> getTradesByStockSymbol(String stockSymbol) {
		return trades.stream()
			   .filter((trade) -> trade.getStock().getStockSymbol().equals(stockSymbol))
			   .collect(Collectors.toList());
	}
	
	/**
	 * This method retrieve all trades from the memory of a particular type
	 */
	
	public List<Trade> getTradesByTradeType(TradeType tradeType) {
		return trades.stream()
				.filter((trade) -> trade.getType().equals(tradeType))
				.collect(Collectors.toList());
	}
	
	public Trade getAnInvalidTrade() {
		return new Trade(TradeType.BUY, 0, -100.00, LocalDateTime.now(), null);
	}
	
}

