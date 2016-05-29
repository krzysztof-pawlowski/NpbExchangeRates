package pl.parser.nbp;

import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import pl.parser.nbp.metrics.RatesMetric;
import rx.Observable;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class NbpRates {

    public Observable<List<CurrencyRates>> fetchRatesForPeriod(CurrencyCode currencyCode, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public Observable<Double> calculateMetric(Observable<List<CurrencyRates>> rates, RatesMetric ratesMetric) {
        return rates.map(ratesMetric::calculate);
    }

}
