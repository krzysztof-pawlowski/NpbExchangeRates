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
    public TablePosition setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
        return this;
    }

    public String getScaler() {
        return scaler;
    }

    @XmlElement( name = "przelicznik" )
    public TablePosition setScaler(String scaler) {
        this.scaler = scaler;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @XmlElement( name = "kod_waluty" )
    public TablePosition setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    @XmlElement( name = "kurs_kupna" )
    @XmlJavaTypeAdapter(value = DoubleAdapter.class)
    public TablePosition setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
        return this;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    @XmlElement( name = "kurs_sprzedazy" )
    @XmlJavaTypeAdapter(value = DoubleAdapter.class)
    public TablePosition setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }
}
