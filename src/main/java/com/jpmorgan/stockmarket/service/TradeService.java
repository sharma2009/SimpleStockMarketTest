package com.jpmorgan.stockmarket.service;

import com.jpmorgan.stockmarket.exception.GenericException;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;

import java.util.List;

/**
 * Created by Neha Sharma on 11/16/2016.
 */
public interface TradeService {

  /**
   * Record a {@code Trade}
   * @param trade
   */
  public void recordTrade(Trade trade);

  /**
   * Get a list of {@code Trade}s for {@code Stock} within the last x minutes
   * @param stock
   * @param numberOfMinutes
   * @return
   */
  public List<Trade> getTrades(Stock stock, int numberOfMinutes) throws GenericException;

  /**
   * Get all {@code Trade}s
   * @return
   */
  public List<Trade> getAllTrades();
}
