package pl.parser.nbp.downloader;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public enum TableType {
    BUY_SELL_RATES("c");

    private String value;

    TableType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
