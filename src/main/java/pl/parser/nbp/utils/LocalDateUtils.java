package pl.parser.nbp.utils;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Utils class for LocalDate.
 */
public class LocalDateUtils {

    /**
     * Get all the dates in the range.
     * @param startDate start date (inclusive) of the range
     * @param endDate end date (inclusive) of the range
     * @return list of dates
     */
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
