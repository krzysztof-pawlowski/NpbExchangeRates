package pl.parser.nbp.utils;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
public class LocalDateUtils {

    public static List<LocalDate> getDatesInRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> datesInRange = new LinkedList<>();
        LocalDate date = startDate;

        while (date.isBefore(endDate)) {
            datesInRange.add(date);
            date = date.plusDays(1);
        }

        datesInRange.add(endDate);
        return datesInRange;
    }
}
