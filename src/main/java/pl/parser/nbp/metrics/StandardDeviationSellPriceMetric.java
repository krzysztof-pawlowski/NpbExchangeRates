package pl.parser.nbp.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Metric calculating standard deviation of sell price of the list of currency rates.
 */
public class StandardDeviationSellPriceMetric implements RatesMetric {

    final static Logger logger = LoggerFactory.getLogger(StandardDeviationSellPriceMetric.class);

    @Override public double calculate(List<CurrencyRates> rates) {

        List<Double> sellPrices = rates.stream()
            .map(currencyRates -> currencyRates.getSellPrice())
            .collect(Collectors.toList());

        double value = Math.sqrt(getVariance(sellPrices));

        logger.debug("StandardDeviationSellPriceMetric value: " + value);

        return value;
    }

    private double getVariance(List<Double> sellPrices) {
        double mean = getMean(sellPrices);
        double temp = 0;
        for (double sellPrice : sellPrices) {
            temp += (mean - sellPrice) * (mean - sellPrice);
        }
        return temp / sellPrices.size();
    }

    private double getMean(List<Double> sellPrices) {
        return sellPrices.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .getAsDouble();
    }

}
