package com.jpmorgan.stockmarket.service.impl;

import com.jpmorgan.stockmarket.dao.StockDao;
import com.jpmorgan.stockmarket.dao.impl.StockDaoImpl;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;
import com.jpmorgan.stockmarket.model.enums.StockType;
import com.jpmorgan.stockmarket.service.StockService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by Neha Sharma on 11/16/2016.
 */

public class StockServiceImpl implements StockService {

    private static StockServiceImpl instance = null;
    private static int ROUND_OFF_PLACE_VALUE = 2;

    public static StockServiceImpl getInstance() {
        if (instance == null) {
            instance = new StockServiceImpl();
        }
        return instance;
    }

    private StockDao stockDao = new StockDaoImpl();


    public void addStock(Stock stock) {
        stockDao.addStock(stock);
    }


    public Stock getStock(String symbol) {
        return stockDao.getStock(symbol);
    }


    public double calculateDividendYield(Stock stock, double price) {
        if (StockType.PREFERRED.equals(stock.getType())) {
            return (stock.getFixedDividend() * stock.getParValue()) / price;
        }
        double result = stock.getLastDividend() / price;
        return round(result, ROUND_OFF_PLACE_VALUE);
    }


    public double calculatePERatio(Stock stock, double price) {
        double result = price / stock.getLastDividend();
        return round(result, ROUND_OFF_PLACE_VALUE);
    }


    public double calculateVolumeWeightedStockPrice(List<Trade> trades) {
        double sumOfPriceQuantity = 0;
        int sumOfQuantity = 0;

        for (Trade trade : trades) {
            sumOfPriceQuantity = sumOfPriceQuantity + (trade.getPrice() * trade.getQuantity());
            sumOfQuantity = sumOfQuantity + trade.getQuantity();
        }
        double result = sumOfPriceQuantity / sumOfQuantity;
        return round(result, ROUND_OFF_PLACE_VALUE);
    }


    public double calculateGBCE(List<Trade> trades) {
        double total = 1;
        for (Trade trade : trades) {
            total = total * trade.getPrice();
        }
        double result = Math.pow(total, (1D / trades.size()));
        return round(result, ROUND_OFF_PLACE_VALUE);
    }

    /**
     * Round up to number of places
     *
     * @param value
     * @param places
     * @return
     */
    private static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
