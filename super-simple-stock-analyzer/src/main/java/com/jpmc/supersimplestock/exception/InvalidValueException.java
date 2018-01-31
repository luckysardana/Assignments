package com.jpmc.supersimplestock.exception;

public class InvalidValueException extends RuntimeException{
	
	/**
	 * Custom Exception class for Invalid Stock or Trade details 
	 */
	private static final long serialVersionUID = 1135688719642730441L;

	public InvalidValueException(String message) {
		super(message);
	}
}
