package pl.parser.nbp.downloader;

import rx.Observable;

import java.time.LocalDate;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class RatesFilenameProvider {

    private static final String DIR_FILE_URL_PREFIX = "http://www.nbp.pl/kursy/xml/";


    public Observable<String> getRatesFilename(TableType tableType, LocalDate date) {

        String dirFileName = getDirFilename(date);


        return null;
    }

    private String getDirFilename(LocalDate date) {
        LocalDate dateNow = LocalDate.now();
        if (date.getYear() == dateNow.getYear()) {
            return "dir.txt";
        }
        return "dir" + date.getYear() + ".txt";
    }

}
