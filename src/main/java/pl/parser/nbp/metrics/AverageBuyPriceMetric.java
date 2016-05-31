package pl.parser.nbp.metrics;

import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;

/**
 * Metric calculating average buy price of the list of currency rates.
 */
public class AverageBuyPriceMetric implements RatesMetric {
    @Override public double calculate(List<CurrencyRates> rates) {
        return rates.stream()
            .mapToDouble(CurrencyRates::getBuyPrice)
            .average()
            .getAsDouble();
    }
}
