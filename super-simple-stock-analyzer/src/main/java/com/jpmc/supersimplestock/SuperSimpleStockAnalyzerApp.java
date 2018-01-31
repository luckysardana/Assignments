package com.jpmc.supersimplestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class SuperSimpleStockAnalyzerApp {

	public static void main( String[] args )
	{
		SpringApplication.run(SuperSimpleStockAnalyzerApp.class, args);
	}
}
