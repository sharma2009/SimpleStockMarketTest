package com.jpmorgan.stockmarket.test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;
import com.jpmorgan.stockmarket.model.enums.StockType;
import com.jpmorgan.stockmarket.model.enums.TradeType;
import com.jpmorgan.stockmarket.service.StockService;
import com.jpmorgan.stockmarket.service.impl.StockServiceImpl;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Neha Sharma on 11/16/2016.
 */
public class StockServiceImplTest {

  private static final int DELTA = 0;
  private StockService stockService;
  private Stock stock1;
  private Stock stock2;
  private Stock stock3;

  @Before
  public void setup() {
    stockService = new StockServiceImpl();
    stock1 = new Stock("STOCK1", StockType.COMMON, 4, 0, 10);
    stock2 = new Stock("STOCK2", StockType.PREFERRED, 3, 2, 1);
  }

  @Test
  public void testAddAndGetStock() {
    stockService.addStock(stock1);
    Stock result = stockService.getStock(stock1.getSymbol());
    assertEquals(stock1, result);
  }

  @Test
  public void testCalculateDividendYield() {
    assertEquals(1.6, stockService.calculateDividendYield(stock1, 2.5), 0);
  }


  @Test
  public void testCalculateDividendYieldPrefered() {
    assertEquals(0.8, stockService.calculateDividendYield(stock2, 2.5), 0);
  }

  @Test
  public void testCalculatePERatio() {
    assertEquals(0.63, stockService.calculatePERatio(stock1, 2.5), 0);
  }

  @Test
  public void testCalculateVolumeWeightedStockPrice() {
    List<Trade> trades = new ArrayList<Trade>();
    trades.add(new Trade(stock1, Calendar.getInstance().getTime(), 1, TradeType.BUY, 2));
    trades.add(new Trade(stock1, Calendar.getInstance().getTime(), 3, TradeType.BUY, 1.5));
    trades.add(new Trade(stock1, Calendar.getInstance().getTime(), 1, TradeType.BUY, 3));
    assertEquals(1.9, stockService.calculateVolumeWeightedStockPrice(trades), 0);
  }

  @Test
  public void testCalculateGBCE() {
    List<Trade> trades = new ArrayList<Trade>();
    trades.add(new Trade(stock1, Calendar.getInstance().getTime(), 1, TradeType.BUY, 2));
    trades.add(new Trade(stock2, Calendar.getInstance().getTime(), 3, TradeType.BUY, 1.5));
    trades.add(new Trade(stock3, Calendar.getInstance().getTime(), 1, TradeType.BUY, 3));
    assertEquals(2.08, stockService.calculateGBCE(trades), DELTA);
  }
}
