package com.jpmc.supersimplestock.model;

public class Stock {

	private final String stockSymbol; // unique representation for a stock
	private final StockType type; 
	private final Double lastDividend;
	private final Double fixedDividend;
	private final Double parValue; // price at the time of purchase
	
	public Stock(final String stockSymbol, final StockType type, final Double lastDividend, final Double fixedDividend, final Double parValue) {
		super();
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}
	
	public Double getLastDividend() {
		return lastDividend;
	}
	
	public Double getFixedDividend() {
		return fixedDividend;
	}
	
	public Double getParValue() {
		return parValue;
	}
	
	public StockType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Stock Details for " + stockSymbol + ": , type=" + type + ", lastDividend=" + lastDividend
				+ ", fixedDividend=" + fixedDividend + ", parValue=" + parValue;
	}
	
}
