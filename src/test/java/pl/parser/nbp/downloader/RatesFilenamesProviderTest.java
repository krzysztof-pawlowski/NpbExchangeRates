package pl.parser.nbp.downloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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

    private String responseBodyDirTxt = "c001YY0104\n"
        + "h001zYY0104\n"
        + "a001zYY0104\n"
        + "c002zYY0105\n"
        + "h002zYY0105\n"
        + "a002zYY0105\n"
        + "b001zYY0105\n"
        + "c003zYY0107\n"
        + "h003zYY0107\n"
        + "a003zYY0107\n"
        + "c004zYY0108\n"
        + "h004zYY0108\n"
        + "a004zYY0108\n"
        + "c005zYY0111\n"
        + "h005zYY0111";

    private RatesFilenamesProvider ratesFilenamesProvider;

    @Before
    public void before() {
        ratesFilenamesProvider = new RatesFilenamesProvider(asyncHttpClient);
    }

    @Test
    public void getRatesFilenamesFromCurrentYearTest() {
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
        verify(asyncHttpClient).performGetRequest("dir.txt");
        assertThat(ratesFilenames).hasSize(2);
        assertThat(ratesFilenames).contains("c003z" + yearStr + "0107", "c004z" + yearStr + "0108");
    }

    @Test
    public void getRatesFilenamesFromPreviousYearTest() {
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
        verify(asyncHttpClient).performGetRequest("dir2015.txt");
        assertThat(ratesFilenames).hasSize(2);
        assertThat(ratesFilenames).contains("c003z" + yearStr + "0107", "c004z" + yearStr + "0108");
    }

    @Test
    public void getRatesFilenamesFromTwoYearsTest() {
        // GIVEN
        String responseBody2014 = "c251z141230\n"
            + "h251z141230\n"
            + "a251z141230\n"
            + "c251z141230\n"
            + "h252z141231\n"
            + "a252z141231\n"
            + "b052z141231";

        String responseBody2015 = "c001z150102\n"
            + "h001z150102\n"
            + "a001z150102\n"
            + "c251z141230\n"
            + "h002z150105\n"
            + "a002z150105\n"
            + "c003z150107\n"
            + "h003z150107";

        LocalDate startDate = LocalDate.of(2014, 12, 30);
        LocalDate endDate = LocalDate.of(2015,1, 6);
        TableType tableType = TableType.BUY_SELL_RATES;

        when(asyncHttpClient.performGetRequest("dir2014.txt"))
            .thenReturn(Observable.just(responseBody2014));
        when(asyncHttpClient.performGetRequest("dir2015.txt"))
            .thenReturn(Observable.just(responseBody2015));

        // WHEN
        List<String> ratesFilenames = ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .toBlocking().single();

        // THEN
        verify(asyncHttpClient).performGetRequest("dir2014.txt");
        verify(asyncHttpClient).performGetRequest("dir2015.txt");
        assertThat(ratesFilenames).hasSize(3);
        assertThat(ratesFilenames).contains("c251z141230", "c251z141230", "c251z141230");
    }
}
