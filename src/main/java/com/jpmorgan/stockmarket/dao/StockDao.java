package com.jpmorgan.stockmarket.dao;


import com.jpmorgan.stockmarket.model.Stock;
/**
 * Created by Neha Sharma on 11/16/2016.
 */

public interface StockDao {

  /**
   * Add new {@code Stock} item to the db.
   * @param stock
   */
  void addStock(Stock stock);

  /**
   * Get {@code Stock} by stock symbol.
   * @param symbol
   * @return
   */
  Stock getStock(String symbol);

}
