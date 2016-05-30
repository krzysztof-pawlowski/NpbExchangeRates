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
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class NbpRates {

    static final String NBP_RATES_HOST = "www.nbp.pl";

    public Observable<List<CurrencyRates>> fetchRatesForPeriod(CurrencyCode currencyCode, LocalDate startDate, LocalDate endDate) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(NBP_RATES_HOST);
        RatesFilenamesProvider ratesFilenamesProvider = new RatesFilenamesProvider(asyncHttpClient);
        RatesDownloader ratesDownloader = new RatesDownloader(asyncHttpClient, ratesFilenamesProvider);

        return ratesDownloader.getCurrencyRates(startDate, endDate, currencyCode, TableType.BUY_SELL_RATES)
            .map(currencyRates -> {
                asyncHttpClient.close();
                return currencyRates;
            });
    }

    public Observable<Double> calculateMetric(Observable<List<CurrencyRates>> rates, RatesMetric ratesMetric) {
        return rates.map(ratesMetric::calculate);
    }

}
