package pl.parser.nbp;

/**
 * Created by Krzysztof on 28/05/16.
 */
public enum CurrencyCode {
    USD("USD"),
    EUR("EUR"),
    CHF("CHF"),
    GBP("GBP");

    private String code;

    CurrencyCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
