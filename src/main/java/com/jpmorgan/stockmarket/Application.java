package com.jpmorgan.stockmarket;

import com.jpmorgan.stockmarket.exception.GenericException;
import com.jpmorgan.stockmarket.model.Stock;
import com.jpmorgan.stockmarket.model.Trade;
import com.jpmorgan.stockmarket.model.enums.StockType;
import com.jpmorgan.stockmarket.model.enums.TradeType;
import com.jpmorgan.stockmarket.service.StockService;
import com.jpmorgan.stockmarket.service.TradeService;
import com.jpmorgan.stockmarket.service.impl.StockServiceImpl;
import com.jpmorgan.stockmarket.service.impl.TradeServiceImpl;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Neha Sharma on 11/16/2016.
 * This application executes the operation on stock on the basis of user inputs.
 */
public class Application {

    private static StockService stockService = StockServiceImpl.getInstance();
    private static TradeService tradeService = TradeServiceImpl.getInstance();

    private static Scanner scanner;

    public static void main(String[] args) {
        initStocks();
        listAppFunctionality();

        scanner = new Scanner(System.in);
        String choice= null;
        while (true) {
            choice = scanner.nextLine();
            if ("0".equals(choice)) {
                scanner.close();
                System.exit(0);
            } else {
                try {
                    int option = Integer.parseInt(choice);
                    Stock stockFromUser;
                    double priceFromUser;

                    switch (option) {
                        case 1:
                            stockFromUser = getStockFromUser();
                            priceFromUser = getStockPriceFromUser();
                            calculateDividendYield(stockFromUser, priceFromUser);
                            break;
                        case 2:
                            stockFromUser = getStockFromUser();
                            priceFromUser = getStockPriceFromUser();
                            calculatePERatio(stockFromUser, priceFromUser);
                            break;
                        case 3:
                            stockFromUser = getStockFromUser();
                            int quantityFromUser = getQuantityFromUser();
                            TradeType tradeTypeFromUser = getTradeType();
                            priceFromUser = getStockPriceFromUser();
                            recordTrade(stockFromUser, quantityFromUser, tradeTypeFromUser, priceFromUser);
                            break;
                        case 4:
                            stockFromUser = getStockFromUser();
                            calculateVolumeWeightedStockPrice(stockFromUser);
                            break;
                        case 5:
                            calculateGBCE();
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    printResult("Please enter a valid option [0 to 5]");
                } catch (GenericException e1) {
                    printResult(e1.getMessage());
                }
                System.out.println("");
                listAppFunctionality();
            }
        }
    }

    private static Stock getStockFromUser() throws GenericException {
        System.out.println("Please input stock symbol[TEA/GIN/ALE/POP/JOE] e.g:- TEA");
        String stockSymbol = scanner.nextLine().toUpperCase();

            Stock stock = stockService.getStock(stockSymbol);
        if (stock == null) {
            throw new GenericException("Stock not found");
        }
        return stock;
    }

    private static double getStockPriceFromUser() throws GenericException {
        System.out.println("Please input stock price");
        String stockPrice = scanner.nextLine();
        try {
            double result = Double.parseDouble(stockPrice);
            if (result <= 0) {
                throw new GenericException("Invalid price: Must be greater than 0");
            }
            return result;
        } catch (NumberFormatException e) {
            throw new GenericException("Invalid price: please enter a valid number");
        }
    }

    private static TradeType getTradeType() throws GenericException {
        System.out.println("Please input trade type (BUY/SELL)");
        String type = scanner.nextLine();
        try {
            return TradeType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GenericException("Invalid trade type: Must be BUY or SELL");
        }
    }

    private static int getQuantityFromUser() throws GenericException {
        System.out.println("Please input quantity");
        String quantity = scanner.nextLine();
        try {
            int result = Integer.parseInt(quantity);
            if (result <= 0) {
                throw new GenericException("Invalid quantity: Must be greater than 0");
            }
            return result;
        } catch (NumberFormatException e) {
            throw new GenericException("Invalid quantity: please enter a valid number");
        }
    }

    private static void listAppFunctionality() {
        System.out.println("Neha Sharma - Super simple stock market Application test");
        System.out.println("Please select the operation from 0..5 to be performed on stock");
        System.out.println("1: Calculate dividend yield for stock");
        System.out.println("2: Calculate P/E ratio for stock");
        System.out.println("3: Record a trade for stock");
        System.out.println("4: Calculate Volume Weighted Stock Price for stock");
        System.out.println("5: Calculate GBCE All Share Index");
        System.out.println("0: Exit");
    }

    private static void calculateDividendYield(Stock stock, double price) {
        double result = stockService.calculateDividendYield(stock, price);
        printResult("Dividend Yield: " + result);
    }

    private static void calculatePERatio(Stock stock, double price) {
        double result = stockService.calculatePERatio(stock, price);
        printResult("PE Ratio: " + result);
    }

    private static void calculateVolumeWeightedStockPrice(Stock stock) throws GenericException {
        List<Trade> trades = tradeService.getTrades(stock, 15);
        if (trades == null || trades.isEmpty()) {
            printResult("Volume Weighted Stock Price: No trades");
        } else {
            double result = stockService.calculateVolumeWeightedStockPrice(trades);
            printResult("Volume Weighted Stock Price: " + result);
        }
    }

    private static void recordTrade(Stock stock, int quantity, TradeType type, double price) {
        tradeService.recordTrade(new Trade(stock, Calendar.getInstance().getTime(),
                quantity, type, price));
        printResult("Trade recorded");
    }

    private static void calculateGBCE() {
        List<Trade> allTrades = tradeService.getAllTrades();
        if (allTrades == null || allTrades.isEmpty()) {
            printResult("Unable to calculate GBCE: No trades");
        } else {
            printResult("GBCE: " + stockService.calculateGBCE(allTrades));
        }
    }

    private static void initStocks() {
        stockService.addStock(new Stock("TEA", StockType.COMMON, 0, 0, 100));
        stockService.addStock(new Stock("POP", StockType.COMMON, 8, 0, 100));
        stockService.addStock(new Stock("ALE", StockType.COMMON, 23, 0, 60));
        stockService.addStock(new Stock("GIN", StockType.PREFERRED, 8, 2, 100));
        stockService.addStock(new Stock("JOE", StockType.COMMON, 13, 0, 250));
    }

    private static void printResult(String result) {
        System.out.println("-------------------------------------");
        System.out.println(result);
        System.out.println("-------------------------------------");
    }
}
