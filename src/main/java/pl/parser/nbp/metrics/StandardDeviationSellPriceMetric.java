package pl.parser.nbp.metrics;

import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Metric calculating standard deviation of sell price of the list of currency rates.
 */
public class StandardDeviationSellPriceMetric implements RatesMetric {
    @Override public double calculate(List<CurrencyRates> rates) {

        List<Double> sellPrices = rates.stream()
            .map(currencyRates -> currencyRates.getSellPrice())
            .collect(Collectors.toList());

        return Math.sqrt(getVariance(sellPrices));
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
