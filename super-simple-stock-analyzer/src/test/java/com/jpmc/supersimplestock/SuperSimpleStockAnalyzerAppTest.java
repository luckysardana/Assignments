package com.jpmc.supersimplestock;

import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.jpmc.supersimplestock.dao.StockData;
import com.jpmc.supersimplestock.exception.InvalidValueException;
import com.jpmc.supersimplestock.model.Stock;
import com.jpmc.supersimplestock.model.StockType;
import com.jpmc.supersimplestock.model.Trade;
import com.jpmc.supersimplestock.model.TradeType;
import com.jpmc.supersimplestock.service.StockService;
import com.jpmc.supersimplestock.service.TradeService;
import static org.hamcrest.CoreMatchers.is;
import com.jpmc.supersimplestock.dao.TradeData;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Unit tests for simple SuperSimpleStockApp.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SuperSimpleStockAnalyzerConfig.class})
public class SuperSimpleStockAnalyzerAppTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();
	private Map<String, Stock> stockKeyAndStockMap;
	private Map<String, Double> stockKeyAndMarketPriceMap;
	
	@Autowired
	private StockData stockData;
	@Autowired
	private StockService stockService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeData tradeData;

	/*
	 * @throws Exception
	 */
	@Before
	public void setUpAndCleanUp() throws Exception{
		stockKeyAndMarketPriceMap = stockData.getStockMarketPriceMap();
		stockKeyAndStockMap = stockData.getSymbolAndStockMap();
		if(tradeData.getAllTrades() !=null && !tradeData.getAllTrades().isEmpty())  
			tradeData.getAllTrades().clear();
	}
	
	
	@Test
	public void testGivenInvalidStockData_shouldThrowException() {
		thrown.expect(InvalidValueException.class);
	    thrown.expectMessage("Invalid value of Last Dividend");
	    Stock invalidStockDetail  = new Stock("POP", StockType.COMMON, -8.0, -0.0, 100.0);
		stockService.calculateDividendYield(invalidStockDetail, -180.00);
		
	}
	
	@Test
	public void testGivenInvalidTradeDetails_shouleThrowException() {
		thrown.expect(InvalidValueException.class);
	    thrown.expectMessage("A valid stock is required for trading");
	    Trade anInvalidTradeDetails = new Trade(TradeType.BUY, 0, -100.00, LocalDateTime.now(), null);
	    tradeService.addStockTrade(anInvalidTradeDetails);
	}
	
	@Test
	public void testGivenValidStockData_shouldCheckDividendYieldValue() {
		Stock sampleStockDetailsForTEA = stockData.findByStockSymbol("TEA");
		double dividendYieldForTEA = stockService.calculateDividendYield( sampleStockDetailsForTEA, 110) ;
		assertThat( dividendYieldForTEA, is(0.0));
		
		Stock sampleStockDetailsForGIN = stockData.findByStockSymbol("GIN");
		double dividendYieldForGIN = stockService.calculateDividendYield(sampleStockDetailsForGIN, 90) ;
		assertThat( dividendYieldForGIN, is(0.2222222222222222));
	}
	
	@Test
	public void testGivenValidStockData_shouldCheckPnERatioValue() {
		
		Stock sampleStockDetailsForTEA = stockData.findByStockSymbol("TEA");
		double dividendYieldForTEA = stockService.calculateDividendYield( sampleStockDetailsForTEA, 110) ;
		double pAndERationForTEA = stockService.calculatePriceToEarningRatio(110, dividendYieldForTEA);
		
		assertThat(pAndERationForTEA, is(0.0));
		
		Stock sampleStockDetailsForGIN = stockData.findByStockSymbol("GIN");
		double dividendYieldForGIN = stockService.calculateDividendYield(sampleStockDetailsForGIN, 90) ;
		double pAndERationForGIN = stockService.calculatePriceToEarningRatio(90, dividendYieldForGIN);
		assertThat(pAndERationForGIN, is(405.0));//0.0
		
		
	}
	
	@Test
	public void testGivenValidTradeData_shouldCheckSaveStockTrading() {
		
		Trade popTrade = new Trade(TradeType.BUY, 2, 100.00, LocalDateTime.now(), stockData.findByStockSymbol("POP"));
		Trade aleTrade = new Trade(TradeType.SELL, 2, 60.00, LocalDateTime.now(), stockData.findByStockSymbol("ALE"));
		tradeData.addTrade(popTrade);
		tradeData.addTrade(aleTrade);

		assertThat(tradeData.getAllTrades().size(), is(2));
		assertThat(tradeData.getAllTrades().stream().filter(trade -> trade.getType().equals(TradeType.BUY)).count(), is(1L));
		assertThat(tradeData.getAllTrades().stream().filter(trade -> trade.getType().equals(TradeType.SELL)).count(), is(1L));
		
	}
	/**
	 * this method takes given stock with their market price and evaluates allStockIndexValue and assertWithExpectedValue
	 */
	@Test
	public void testGivenValidStockData_shouldCheckAllStockIndexValue() {
		double allShareIndexValue = Math.round(stockService.allShareIndex(stockKeyAndStockMap) * 100D) / 100D;
		assertThat(allShareIndexValue, is(3.62));
		
	}
	
	@Test
	public void testGivenValidTradeData_shouldCheckVolumeWeightedStockPrice_forLast15minsTrades() {
		
		Trade popTrade = new Trade(TradeType.BUY, 2, 100.00, LocalDateTime.now().minusMinutes(20), stockData.findByStockSymbol("POP"));
		Trade aleTrade = new Trade(TradeType.SELL, 2, 60.00, LocalDateTime.now(), stockData.findByStockSymbol("ALE"));
		Trade joeTrade = new Trade(TradeType.SELL, 2, 60.00, LocalDateTime.now(), stockData.findByStockSymbol("JOE"));
		// the popTrade happened more than 15 minutes before therefore will not be picked up for VWSP Evaluation
		tradeData.addTrade(popTrade); 
		tradeData.addTrade(aleTrade);
		tradeData.addTrade(joeTrade);
		
		double volumeWeightedStockPrice = tradeService.calculateVolumeWeightedStockPriceInTimeRange();
		assertThat(volumeWeightedStockPrice, is(60.0));
	}
	
}
