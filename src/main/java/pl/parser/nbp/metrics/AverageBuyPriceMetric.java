package pl.parser.nbp.metrics;

import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class AverageBuyPriceMetric implements RatesMetric {
    @Override public double calculate(List<CurrencyRates> rates) {
        return rates.stream()
            .mapToDouble(CurrencyRates::getBuyPrice)
            .average()
            .getAsDouble();
    }
}
