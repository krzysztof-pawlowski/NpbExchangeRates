package pl.parser.nbp.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.parser.nbp.dto.CurrencyRates;

import java.util.List;

/**
 * Metric calculating average buy price of the list of currency rates.
 */
public class AverageBuyPriceMetric implements RatesMetric {

    final static Logger logger = LoggerFactory.getLogger(AverageBuyPriceMetric.class);

    @Override public double calculate(List<CurrencyRates> rates) {
        logger.debug("Calculating AverageBuyPriceMetric...");
        double value =  rates.stream()
            .mapToDouble(CurrencyRates::getBuyPrice)
            .average()
            .getAsDouble();

        logger.debug("AverageBuyPriceMetric value: " + value);

        return value;
    }
}
