package pl.parser.nbp.metrics;

import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class StandardDeviationSellPriceMetric implements RatesMetric {
    @Override public double calculate(List<CurrencyRates> rates) {
        return 0;
    }
}
