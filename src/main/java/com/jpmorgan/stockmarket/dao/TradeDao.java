package com.jpmorgan.stockmarket.dao;

import com.jpmorgan.stockmarket.exception.GenericException;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;

import java.util.List;

/**
 * Created by Neha Sharma on 11/16/2016.
 */

public interface TradeDao {

  /**
   * Add {@code Trade} to the db
   * @param trade
   */
  void addTrade(Trade trade);

  /**
   * Get all {@code Trade} for supplied stock in the last x minutes
   * @param stock
   * @param minutes
   * @return
   */
  List<Trade> getTrades(Stock stock, int minutes) throws GenericException;

  /**
   * Get all {@code Trade} for all stocks
   * @return
   */
  List<Trade> getAllTrades();

}
