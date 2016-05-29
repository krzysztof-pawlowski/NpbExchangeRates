package pl.parser.nbp;

import pl.parser.nbp.dto.CurrencyCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        System.out.println(currencyCode);
        System.out.println(startDate);
        System.out.println(endDate);

    }

    private static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            System.out.println(ex.getMessage());
        }
        System.exit(0);
        return null;
    }

    private static CurrencyCode parseCurrencyCode(String codeString) {
        try {
            return CurrencyCode.valueOf(codeString);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown currency code: " + codeString);
        }
        System.exit(0);
        return null;
    }
}
