package pl.parser.nbp.downloader;

import pl.parser.nbp.utils.LocalDateUtils;
import rx.Observable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class RatesFilenamesProvider {

    static final String DIR_FILE_URL_PREFIX = "www.nbp.pl";

    private AsyncHttpClient asyncHttpClient;

    public RatesFilenamesProvider() {
        this(new AsyncHttpClient(DIR_FILE_URL_PREFIX));
    }

    public RatesFilenamesProvider(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    public Observable<List<String>> getRatesFilenames(LocalDate startDate, LocalDate endDate, TableType tableType) {

        List<LocalDate> datesInRange = LocalDateUtils.getDatesInRange(startDate, endDate);

        Map<Integer, List<LocalDate>> datesGroupedByYear = datesInRange.stream()
            .collect(Collectors.groupingBy(LocalDate::getYear));

        Set<Observable<List<String>>> ratesFilenamesForYearsObservables = datesGroupedByYear.keySet().stream()
            .map(year -> getRatesFilenamesForYear(year, datesGroupedByYear.get(year), tableType))
            .collect(Collectors.toSet());

        return Observable.merge(ratesFilenamesForYearsObservables)
            .toList()
            .map(listOfLists -> listOfLists.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    private Observable<List<String>> getRatesFilenamesForYear(int year, List<LocalDate> dates, TableType tableType) {
        String dirFileName = getDirFilename(year);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        Set<String> dateSuffixes = dates.stream()
            .map(formatter::format)
            .collect(Collectors.toSet());

        return asyncHttpClient.performGetRequest(dirFileName)
            .map(dirFile -> Arrays.asList(dirFile.split("\r\n")))
            .map(filenames -> filenames.stream()
                    .filter(filename -> filename.startsWith(tableType.value()))
                    .collect(Collectors.toList()))
            .map(filenames -> filenames.stream()
                    .filter(filename -> dateSuffixes.stream().anyMatch(filename::endsWith))
                    .collect(Collectors.toList()));
    }

    private String getDirFilename(int year) {
        LocalDate dateNow = LocalDate.now();
        if (year == dateNow.getYear()) {
            return "/kursy/xml/dir.txt";
        }
        return "/kursy/xml/dir" + year + ".txt";
    }

}
