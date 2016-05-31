package pl.parser.nbp.downloader.model;

import pl.parser.nbp.utils.jaxb.DoubleAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Class representing the table position from NBP rates server
 */
public class TablePosition {

    private String currencyName;
    private String scaler;
    private String currencyCode;
    private Double buyPrice;
    private Double sellPrice;

    /**
     * Gets the name of the currency.
     * @return name of the currency
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * Sets the name of the currency.
     * @param currencyName name of the currency
     * @return table position object
     */
    @XmlElement( name = "nazwa_waluty" )
    public TablePosition setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
        return this;
    }

    /**
     * Gets the scaler.
     * @return scaler
     */
    public String getScaler() {
        return scaler;
    }

    /**
     * Sets the scaler
     * @param scaler scaler
     * @return table position object
     */
    @XmlElement( name = "przelicznik" )
    public TablePosition setScaler(String scaler) {
        this.scaler = scaler;
        return this;
    }

    /**
     * Gets the currency code.
     * @return currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the currency code.
     * @param currencyCode currency code
     * @return table position object
     */
    @XmlElement( name = "kod_waluty" )
    public TablePosition setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    /**
     * Gets the buy price.
     * @return buy price
     */
    public Double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Sets the buy price.
     * @param buyPrice buy price
     * @return table position object
     */
    @XmlElement( name = "kurs_kupna" )
    @XmlJavaTypeAdapter(value = DoubleAdapter.class)
    public TablePosition setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
        return this;
    }

    /**
     * Get the sell price.
     * @return sell price
     */
    public Double getSellPrice() {
        return sellPrice;
    }

    /**
     * Sets the sell price.
     * @param sellPrice sell price
     * @return table position object
     */
    @XmlElement( name = "kurs_sprzedazy" )
    @XmlJavaTypeAdapter(value = DoubleAdapter.class)
    public TablePosition setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }
}
