package pl.parser.nbp.metrics;

import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public interface RatesMetric {
    double calculate(List<CurrencyRates> rates);
}
