package pl.parser.nbp.metrics;

import org.junit.Before;
import org.junit.Test;
import pl.parser.nbp.dto.CurrencyRates;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Krzysztof Pawlowski on 30/05/16.
 */
public class AverageBuyPriceMetricTest {

    private AverageBuyPriceMetric averageBuyPriceMetric;

    @Before
    public void before() {
        this.averageBuyPriceMetric = new AverageBuyPriceMetric();
    }

    @Test
    public void calculateShouldReturnAverageOfBuyPrices() {
        // GIVEN
        List<CurrencyRates> currencyRates = Arrays.asList(
            new CurrencyRates().setBuyPrice(1),
            new CurrencyRates().setBuyPrice(2)
        );

        // WHEN
        double result = averageBuyPriceMetric.calculate(currencyRates);

        //
        assertThat(result).isEqualTo(1.5);

    }
}
