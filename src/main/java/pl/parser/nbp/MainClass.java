package pl.parser.nbp;

import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import pl.parser.nbp.metrics.AverageBuyPriceMetric;
import rx.Observable;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class MainClass {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Wrong argument count");
            System.out.println("Usage: java pl.parser.nbp.MainClass <currencyCode> <startDate> <endDate>");
            System.out.println("Date format: yyyy-MM-dd");
            return;
        }
        CurrencyCode currencyCode = parseCurrencyCode(args[0]);
        LocalDate startDate = parseDate(args[1]);
        LocalDate endDate = parseDate(args[2]);

        if (currencyCode == null || startDate == null || endDate == null) {
            return;
        }

        NbpRates nbpRates = new NbpRates();
        Observable<List<CurrencyRates>> currencyRatesObservable = nbpRates
            .fetchRatesForPeriod(currencyCode, startDate, endDate).cache();

        double d = nbpRates.calculateMetric(currencyRatesObservable, new AverageBuyPriceMetric())
            .toBlocking().single();

        System.out.println(d);

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
}
