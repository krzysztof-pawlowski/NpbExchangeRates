package pl.parser.nbp.downloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.parser.nbp.http.AsyncHttpClient;
import rx.Observable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RatesFilenamesProviderTest {

    @Mock
    AsyncHttpClient asyncHttpClient;

    private String responseBodyDirTxt = "c001YY0104\r\n"
        + "h001zYY0104\r\n"
        + "a001zYY0104\r\n"
        + "c002zYY0105\r\n"
        + "h002zYY0105\r\n"
        + "a002zYY0105\r\n"
        + "b001zYY0105\r\n"
        + "c003zYY0107\r\n"
        + "h003zYY0107\r\n"
        + "a003zYY0107\r\n"
        + "c004zYY0108\r\n"
        + "h004zYY0108\r\n"
        + "a004zYY0108\r\n"
        + "c005zYY0111\r\n"
        + "h005zYY0111";

    private RatesFilenamesProvider ratesFilenamesProvider;

    @Before
    public void before() {
        ratesFilenamesProvider = new RatesFilenamesProvider(asyncHttpClient);
    }

    @Test
    public void getRatesFilenamesFromCurrentYearShouldGetResultsFromDirFileWithoutYearInName() {
        // GIVEN
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY");
        String yearStr = formatter.format(today);
        int yearInt = today.getYear();
        responseBodyDirTxt = responseBodyDirTxt.replace("YY", yearStr);

        LocalDate startDate = LocalDate.of(yearInt, 1, 6);
        LocalDate endDate = LocalDate.of(yearInt, 1, 8);
        TableType tableType = TableType.BUY_SELL_RATES;

        when(asyncHttpClient.performGetRequest(any()))
            .thenReturn(Observable.just(responseBodyDirTxt));

        // WHEN
        List<String> ratesFilenames = ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .toBlocking().single();

        // THEN
        verify(asyncHttpClient).performGetRequest("/kursy/xml/dir.txt");
        assertThat(ratesFilenames).hasSize(2);
        assertThat(ratesFilenames).contains("c003z" + yearStr + "0107", "c004z" + yearStr + "0108");
    }

    @Test
    public void getRatesFilenamesFromPreviousYearShouldGetResultsFromDirFileWithYearInName() {
        // GIVEN
        String yearStr = "15";
        responseBodyDirTxt = responseBodyDirTxt.replace("YY", yearStr);

        LocalDate startDate = LocalDate.of(2015, 1, 6);
        LocalDate endDate = LocalDate.of(2015,1, 8);
        TableType tableType = TableType.BUY_SELL_RATES;

        when(asyncHttpClient.performGetRequest(any()))
            .thenReturn(Observable.just(responseBodyDirTxt));

        // WHEN
        List<String> ratesFilenames = ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .toBlocking().single();

        // THEN
        verify(asyncHttpClient).performGetRequest("/kursy/xml/dir2015.txt");
        assertThat(ratesFilenames).hasSize(2);
        assertThat(ratesFilenames).contains("c003z" + yearStr + "0107", "c004z" + yearStr + "0108");
    }

    @Test
    public void getRatesFilenamesFromTwoYearsShouldLookForTwoFilesAndMergeResultsFromThem() {
        // GIVEN
        String responseBody2014 = "c251z141230\r\n"
            + "h251z141230\r\n"
            + "a251z141230\r\n"
            + "c251z141230\r\n"
            + "h252z141231\r\n"
            + "a252z141231\r\n"
            + "b052z141231";

        String responseBody2015 = "c001z150102\r\n"
            + "h001z150102\r\n"
            + "a001z150102\r\n"
            + "c251z141230\r\n"
            + "h002z150105\r\n"
            + "a002z150105\r\n"
            + "c003z150107\r\n"
            + "h003z150107";

        LocalDate startDate = LocalDate.of(2014, 12, 30);
        LocalDate endDate = LocalDate.of(2015,1, 6);
        TableType tableType = TableType.BUY_SELL_RATES;

        when(asyncHttpClient.performGetRequest("/kursy/xml/dir2014.txt"))
            .thenReturn(Observable.just(responseBody2014));
        when(asyncHttpClient.performGetRequest("/kursy/xml/dir2015.txt"))
            .thenReturn(Observable.just(responseBody2015));

        // WHEN
        List<String> ratesFilenames = ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .toBlocking().single();

        // THEN
        verify(asyncHttpClient).performGetRequest("/kursy/xml/dir2014.txt");
        verify(asyncHttpClient).performGetRequest("/kursy/xml/dir2015.txt");
        assertThat(ratesFilenames).hasSize(3);
        assertThat(ratesFilenames).contains("c251z141230", "c251z141230", "c251z141230");
    }
}
