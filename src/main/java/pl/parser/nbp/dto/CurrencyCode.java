package pl.parser.nbp.dto;

/**
 * Currency code - value is a string representation of the code
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
