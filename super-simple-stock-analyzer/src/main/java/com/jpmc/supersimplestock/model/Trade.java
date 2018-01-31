package com.jpmc.supersimplestock.model;

import java.time.LocalDateTime;

public class Trade {

	private final TradeType type;
	private final Integer quantity;
	private final Double price;
	private final LocalDateTime localDateTime;
	private final Stock stock;
	
	public Trade(final TradeType type, final Integer quantity, final Double price, final LocalDateTime localDateTime, final Stock stock) {
		super();
		this.type = type;
		this.quantity = quantity;
		this.price = price;
		this.localDateTime = localDateTime;
		this.stock = stock;
	}
	public TradeType getType() {
		return type;
	}
	
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public Stock getStock() {
		return stock;
	}
		
}
