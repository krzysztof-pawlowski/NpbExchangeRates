package pl.parser.nbp.dto;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class CurrencyRates {
    private CurrencyCode currencyCode;
    private double sellPrice;
    private double buyPrice;

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public CurrencyRates setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public CurrencyRates setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public CurrencyRates setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
        return this;
    }
}
