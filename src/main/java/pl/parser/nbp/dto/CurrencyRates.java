package pl.parser.nbp.dto;

import java.time.LocalDate;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class CurrencyRates {
    private CurrencyCode currencyCode;
    private double sellPrice;
    private double buyPrice;
    private LocalDate publicationDate;

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

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public CurrencyRates setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    @Override public String toString() {
        return "CurrencyRates{" +
            "currencyCode=" + currencyCode +
            ", sellPrice=" + sellPrice +
            ", buyPrice=" + buyPrice +
            ", publicationDate=" + publicationDate +
            '}';
    }
}
