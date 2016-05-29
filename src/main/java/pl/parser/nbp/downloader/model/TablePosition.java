package pl.parser.nbp.downloader.model;

import pl.parser.nbp.utils.jaxb.DoubleAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class TablePosition {

    private String currencyName;
    private String scaler;
    private String currencyCode;
    private Double buyPrice;
    private Double sellPrice;

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

    public Double getBuyPrice() {
        return buyPrice;
    }

    @XmlElement( name = "kurs_kupna" )
    @XmlJavaTypeAdapter(value = DoubleAdapter.class)
    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    @XmlElement( name = "kurs_sprzedazy" )
    @XmlJavaTypeAdapter(value = DoubleAdapter.class)
    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }
}
