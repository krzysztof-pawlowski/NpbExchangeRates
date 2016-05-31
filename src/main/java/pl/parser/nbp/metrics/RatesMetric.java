package pl.parser.nbp.metrics;

import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;

/**
 * Interface for metrics giving scalar result on the list of the currency rates
 */
public interface RatesMetric {

    /**
     * Calculates the metric value.
     * @param rates list of currency rates
     * @return metric result
     */
    double calculate(List<CurrencyRates> rates);
}
