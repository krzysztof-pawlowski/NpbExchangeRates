package pl.parser.nbp;

import pl.parser.nbp.downloader.RatesDownloader;
import pl.parser.nbp.downloader.RatesFilenamesProvider;
import pl.parser.nbp.downloader.TableType;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import pl.parser.nbp.http.AsyncHttpClient;
import pl.parser.nbp.metrics.RatesMetric;
import rx.Observable;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.util.List;

/**
 * Facade for getting the rates and calculating the metrics.
 */
public class NbpRates {

    private RatesDownloader ratesDownloader;

    /**
     * The constructor
     * @param ratesDownloader rates downloader
     */
    public NbpRates(RatesDownloader ratesDownloader) {
        this.ratesDownloader = ratesDownloader;
    }

    /**
     * Fetches the rates for the period given.
     * @param currencyCode currency code
     * @param startDate start date (inclusive) of the range
     * @param endDate end date (inclusive) of the range
     * @return list fo currency rates
     */
    public Observable<List<CurrencyRates>> fetchRatesForPeriod(CurrencyCode currencyCode, LocalDate startDate, LocalDate endDate) {
        return ratesDownloader.getCurrencyRates(startDate, endDate, currencyCode, TableType.BUY_SELL_RATES);
    }

    /**
     * Calculates the metric given for the list of currency rates.
     * @param rates list of currency rates
     * @param ratesMetric currency rates metric
     * @return metric value
     */
    public Observable<Double> calculateMetric(Observable<List<CurrencyRates>> rates, RatesMetric ratesMetric) {
        return rates.map(ratesMetric::calculate);
    }

}
