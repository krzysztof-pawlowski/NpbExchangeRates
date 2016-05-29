package pl.parser.nbp.downloader.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class TablePosition {

    private String currencyName;
    private String scaler;
    private String currencyCode;
    private double buyPrice;
    private double sellPrice;

    public String getCurrencyName() {
        return currencyName;
    }

    @XmlElement( name = "nazwa_waluty" )
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getScaler() {
        return scaler;
    }

    @XmlElement( name = "przelicznik" )
    public void setScaler(String scaler) {
        this.scaler = scaler;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @XmlElement( name = "kod_waluty" )
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    @XmlElement( name = "kurs_kupna" )
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    @XmlElement( name = "kurs_sprzedazy" )
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}
