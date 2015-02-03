package edu.cmu.cs.cs214.rec14.exercises.stocks;

import java.util.List;

public class Stock {

    private String name;
    private List<Double> priceHistory;

    public Stock(String name, List<Double> priceHistory) {
        this.name = name;
        this.priceHistory = priceHistory;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Stock))
            return false;

        Stock stock = (Stock) o;

        return name.equals(stock.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
