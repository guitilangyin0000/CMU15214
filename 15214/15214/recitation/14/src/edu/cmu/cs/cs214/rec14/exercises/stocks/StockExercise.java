package edu.cmu.cs.cs214.rec14.exercises.stocks;

import java.util.ArrayList;
import java.util.List;

public class StockExercise {
    private List<Stock> stocks;

    public StockExercise(List<Stock> stocks) {
        this.stocks = stocks;
    }

    /**
     * 1. Return a list of {@link PriceInfo} object containing the price information (min price, max price) for each stock.
     */
    public List<PriceInfo> getPriceInfo() {
        return new ArrayList<>();
    }

    /**
     * 2. Return the largest price range (highest price - lowest price) among all stocks.
     */
    public double maxChange() {
        return 0;
    }


}
