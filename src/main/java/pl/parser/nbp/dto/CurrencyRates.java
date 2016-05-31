package pl.parser.nbp.dto;

import java.time.LocalDate;

/**
 * Class aggregating information about currency rates fetched from the server.
 */
public class CurrencyRates {
    private CurrencyCode currencyCode;
    private double sellPrice;
    private double buyPrice;
    private LocalDate publicationDate;

    /**
     * Gets the currency code.
     * @return currency code
     */
    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the currency code.
     * @param currencyCode currency code
     * @return currency rates object
     */
    public CurrencyRates setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    /**
     * Gets the sell price.
     * @return sell price
     */
    public double getSellPrice() {
        return sellPrice;
    }

    /**
     * Sets the sell price
     * @param sellPrice sell price
     * @return currency rates object
     */
    public CurrencyRates setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    /**
     * Gets the buy price.
     * @return buy price
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Sets the buy price.
     * @param buyPrice buy price
     * @return currency rates object
     */
    public CurrencyRates setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
        return this;
    }

    /**
     * Gets publication date of the rates.
     * @return publication date of the rates
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the publication date of the rates
     * @param publicationDate publication date of the rates
     * @return currency rates object
     */
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
