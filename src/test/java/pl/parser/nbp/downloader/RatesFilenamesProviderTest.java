package pl.parser.nbp.downloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import java.time.LocalDate;
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

    private String responseBodyDirTxt = "c001z160104\n"
        + "h001z160104\n"
        + "a001z160104\n"
        + "c002z160105\n"
        + "h002z160105\n"
        + "a002z160105\n"
        + "b001z160105\n"
        + "c003z160107\n"
        + "h003z160107\n"
        + "a003z160107\n"
        + "c004z160108\n"
        + "h004z160108\n"
        + "a004z160108\n"
        + "c005z160111\n"
        + "h005z160111";

    private RatesFilenamesProvider ratesFilenamesProvider;

    @Before
    public void before() {
        ratesFilenamesProvider = new RatesFilenamesProvider(asyncHttpClient);

        when(asyncHttpClient.performGetRequest(any()))
            .thenReturn(Observable.just(responseBodyDirTxt));
    }

    @Test
    public void getRatesFilenamesTest() {
        // GIVEN
        LocalDate startDate = LocalDate.of(2016, 1, 6);
        LocalDate endDate = LocalDate.of(2016,1, 8);
        TableType tableType = TableType.BUY_SELL_RATES;

        // WHEN
        List<String> ratesFilenames = ratesFilenamesProvider.getRatesFilenames(startDate, endDate, tableType)
            .toBlocking().single();

        // THEN
        verify(asyncHttpClient).performGetRequest("dir.txt");
        assertThat(ratesFilenames).hasSize(2);
        assertThat(ratesFilenames).contains("c003z160107", "c004z160108");
    }
}
