package pl.parser.nbp;

import pl.parser.nbp.downloader.RatesDownloader;
import pl.parser.nbp.downloader.RatesFilenamesProvider;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import pl.parser.nbp.http.AsyncHttpClient;
import pl.parser.nbp.metrics.AverageBuyPriceMetric;
import pl.parser.nbp.metrics.StandardDeviationSellPriceMetric;
import rx.Observable;

import javax.xml.bind.JAXBException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class MainClass {

    private static final String NBP_RATES_HOST = "www.nbp.pl";

    private static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(NBP_RATES_HOST);
    private static final RatesFilenamesProvider ratesFilenamesProvider = new RatesFilenamesProvider(asyncHttpClient);
    private static final RatesDownloader ratesDownloader = new RatesDownloader(asyncHttpClient, ratesFilenamesProvider);

    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage();
            return;
        }
        CurrencyCode currencyCode = parseCurrencyCode(args[0]);
        LocalDate startDate = parseDate(args[1]);
        LocalDate endDate = parseDate(args[2]);

        if (currencyCode == null || startDate == null || endDate == null) {
            return;
        }

        NbpRates nbpRates = new NbpRates(ratesDownloader);

        Observable<List<CurrencyRates>> currencyRatesObservable = nbpRates
            .fetchRatesForPeriod(currencyCode, startDate, endDate).cache();

        DecimalFormat resultDoubleFormat = new DecimalFormat("0.0000");
        double averageBuyPriceMetricResult =
            nbpRates.calculateMetric(currencyRatesObservable, new AverageBuyPriceMetric()).toBlocking().single();

        double standardDeviationSellPriceMetricResult =
            nbpRates.calculateMetric(currencyRatesObservable, new StandardDeviationSellPriceMetric()).toBlocking().single();

        System.out.println(resultDoubleFormat.format(averageBuyPriceMetricResult));
        System.out.println(resultDoubleFormat.format(standardDeviationSellPriceMetricResult));

        asyncHttpClient.close();

    }

    private static LocalDate parseDate(String dateString) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            System.out.println(ex.getMessage());
        }
        return date;
    }

    private static CurrencyCode parseCurrencyCode(String codeString) {
        CurrencyCode code = null;
        try {
            code =  CurrencyCode.valueOf(codeString);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown currency code: " + codeString);
        }
        return code;
    }

    private static void printUsage() {
        System.out.println("Wrong argument count");
        System.out.println("Usage: java pl.parser.nbp.MainClass <currencyCode> <startDate> <endDate>");
        System.out.println("Date format: yyyy-MM-dd");
    }
}
