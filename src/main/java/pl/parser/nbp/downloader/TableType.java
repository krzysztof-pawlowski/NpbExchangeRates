package pl.parser.nbp.downloader;

/**
 * Table type - value is the name of the table in the NBP rates.
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
