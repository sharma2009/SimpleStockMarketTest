package com.jpmorgan.stockmarket.model;

import com.jpmorgan.stockmarket.model.enums.TradeType;

import java.util.Date;

/**
 * Created by Neha Sharma on 11/16/2016.
 */

public class Trade implements Comparable<Trade> {

    private Stock stock;

    private Date timestamp;

    private int quantity;

    private TradeType type;

    private double price;

    public Trade(Stock stock, Date timestamp, int quantity, TradeType type, double price) {
        super();
        this.stock = stock;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TradeType getType() {
        return type;
    }

    public void setType(TradeType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int compareTo(Trade trade) {
        return trade.getTimestamp().compareTo(this.timestamp);
    }

}
