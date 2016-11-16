package com.jpmorgan.stockmarket.dao.impl;

import com.jpmorgan.stockmarket.dao.StockDao;
import com.jpmorgan.stockmarket.model.Stock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neha Sharma on 11/16/2016.
 */

public class StockDaoImpl implements StockDao {

    private Map<String, Stock> stockMap = new HashMap<String, Stock>();

    public void addStock(Stock stock) {
        stockMap.put(stock.getSymbol(), stock);
    }

    public Stock getStock(String symbol) {
        return stockMap.get(symbol);
    }

}
