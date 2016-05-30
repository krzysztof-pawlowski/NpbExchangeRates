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
public class StandardDeviationSellPriceMetricTest {

    private StandardDeviationSellPriceMetric standardDeviationSellPriceMetric;

    @Before
    public void before() {
        this.standardDeviationSellPriceMetric = new StandardDeviationSellPriceMetric();
    }

    @Test
    public void calculateShouldReturnStandardDeviationOfSellPrices() {
        // GIVEN
        List<CurrencyRates> currencyRates = Arrays.asList(
            new CurrencyRates().setSellPrice(1),
            new CurrencyRates().setSellPrice(2)
        );

        // WHEN
        double result = standardDeviationSellPriceMetric.calculate(currencyRates);

        //
        assertThat(result).isEqualTo(0.5);
    }
}
