package com.jpmorgan.stockmarket.service.impl;

import com.jpmorgan.stockmarket.dao.TradeDao;
import com.jpmorgan.stockmarket.dao.impl.TradeDaoImpl;
import com.jpmorgan.stockmarket.exception.GenericException;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;
import com.jpmorgan.stockmarket.service.TradeService;

import java.util.List;

/**
 * Created by Neha Sharma on 11/16/2016.
 */

public class TradeServiceImpl implements TradeService {

    private static TradeServiceImpl instance = null;

    public static TradeService getInstance() {
        if (instance == null) {
            instance = new TradeServiceImpl();
        }
        return instance;
    }

    private TradeDao tradeDao = new TradeDaoImpl();

    public void recordTrade(Trade trade) {
        if (trade != null && trade.getStock() != null) {
            tradeDao.addTrade(trade);
        }
    }

    public List<Trade> getTrades(Stock stock, int minutes) throws GenericException {
        return tradeDao.getTrades(stock, minutes);
    }

    public List<Trade> getAllTrades() {
        return tradeDao.getAllTrades();
    }

}
