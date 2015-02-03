package edu.cmu.cs.cs214.rec14.exercises.stocks;

public class PriceInfo {

    private Stock stock;

    private double minPrice;

    private double maxPrice;

    public PriceInfo(Stock stock, double minPrice, double maxPrice) {
        this.stock = stock;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Stock getStock() {
        return stock;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PriceInfo))
            return false;

        PriceInfo priceInfo = (PriceInfo) o;

        return maxPrice == priceInfo.maxPrice && minPrice == priceInfo.minPrice && stock.equals(priceInfo.stock);

    }

    @Override
    public int hashCode() {
        return stock.hashCode();
    }
}
