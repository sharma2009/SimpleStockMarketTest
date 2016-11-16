package com.jpmorgan.stockmarket.dao.impl;

import com.jpmorgan.stockmarket.dao.TradeDao;
import com.jpmorgan.stockmarket.exception.GenericException;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Neha Sharma on 11/16/2016.
 */

public class TradeDaoImpl implements TradeDao {

    private Map<String, List<Trade>> tradeMap = new HashMap<String, List<Trade>>(); // Performance

    public void addTrade(Trade trade) {
        List<Trade> trades = new ArrayList<Trade>();
        if (tradeMap.containsKey(trade.getStock().getSymbol())) {
            trades = tradeMap.get(trade.getStock().getSymbol());
        }
        trades.add(trade);
        tradeMap.put(trade.getStock().getSymbol(), trades);
    }

    public List<Trade> getTrades(Stock stock, int minutes) throws GenericException {
        List<Trade> result = new ArrayList<Trade>();
        Date afterDate = getDateXMinutesEarlier(minutes);
        List<Trade> trades = tradeMap.get(stock.getSymbol());
        if (null == trades || trades.isEmpty() || trades.size() == 0) {
            throw new GenericException("No trade has been recorded yet for this symbol");
        }
        Collections.sort(trades); // Order by latest trade first.
        Iterator<Trade> it = trades.iterator();
        while (it.hasNext()) {
            Trade trade = it.next();
            if (trade.getTimestamp().before(afterDate)) {
                break;
            }
            result.add(trade);
        }
        return result;
    }

    public List<Trade> getAllTrades() {
        List<Trade> result = new ArrayList<Trade>();
        for (String stockSymbol : tradeMap.keySet()) {
            result.addAll(tradeMap.get(stockSymbol));
        }
        return result;
    }


    private Date getDateXMinutesEarlier(int minutes) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -minutes);
        return c.getTime();
    }

}
