package pl.parser.nbp.downloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.parser.nbp.dto.CurrencyCode;
import pl.parser.nbp.dto.CurrencyRates;
import pl.parser.nbp.http.AsyncHttpClient;
import rx.Observable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Krzysztof Pawlowski on 29/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RatesDownloaderTest {

    @Mock
    AsyncHttpClient asyncHttpClient;

    @Mock
    RatesFilenamesProvider ratesFilenamesProvider;

    private RatesDownloader ratesDownloader;

    @Before
    public void before() {
        this.ratesDownloader = new RatesDownloader(asyncHttpClient, ratesFilenamesProvider);
    }

    @Test
    public void getCurrencyRatesShouldReturnEntityWithAllFieldsFilled() {
        // GIVEN
        LocalDate date = LocalDate.now();

        when(ratesFilenamesProvider.getRatesFilenames(any(), any(), any()))
            .thenReturn(Observable.just(Arrays.asList("file1", "file2")));

        when(asyncHttpClient.performGetRequest("/kursy/xml/file1.xml"))
            .thenReturn(Observable.just("<tabela_kursow typ=\"C\">\n"
                + "<numer_tabeli>73/C/NBP/2007</numer_tabeli>\n"
                + "<data_notowania>2007-04-11</data_notowania>\n"
                + "<data_publikacji>2007-04-12</data_publikacji>\n"
                + "<pozycja>\n"
                + "<nazwa_waluty>dolar amerykanski</nazwa_waluty>\n"
                + "<przelicznik>1</przelicznik>\n"
                + "<kod_waluty>USD</kod_waluty>\n"
                + "<kurs_kupna>2,8210</kurs_kupna>\n"
                + "<kurs_sprzedazy>2,8780</kurs_sprzedazy>\n"
                + "</pozycja>\n"
                + "<pozycja>\n"
                + "<nazwa_waluty>dolar australijski</nazwa_waluty>\n"
                + "<przelicznik>1</przelicznik>\n"
                + "<kod_waluty>AUD</kod_waluty>\n"
                + "<kurs_kupna>2,3292</kurs_kupna>\n"
                + "<kurs_sprzedazy>2,3762</kurs_sprzedazy>\n"
                + "</pozycja>\n"
                + "</tabela_kursow>"));

        when(asyncHttpClient.performGetRequest("/kursy/xml/file2.xml"))
            .thenReturn(Observable.just("<tabela_kursow typ=\"C\">\n"
                + "<numer_tabeli>73/C/NBP/2007</numer_tabeli>\n"
                + "<data_notowania>2007-04-12</data_notowania>\n"
                + "<data_publikacji>2007-04-13</data_publikacji>\n"
                + "<pozycja>\n"
                + "<nazwa_waluty>dolar australijski</nazwa_waluty>\n"
                + "<przelicznik>1</przelicznik>\n"
                + "<kod_waluty>AUD</kod_waluty>\n"
                + "<kurs_kupna>2,8210</kurs_kupna>\n"
                + "<kurs_sprzedazy>2,8780</kurs_sprzedazy>\n"
                + "</pozycja>\n"
                + "<pozycja>\n"
                + "<nazwa_waluty>dolar amerykanski</nazwa_waluty>\n"
                + "<przelicznik>1</przelicznik>\n"
                + "<kod_waluty>USD</kod_waluty>\n"
                + "<kurs_kupna>2,3292</kurs_kupna>\n"
                + "<kurs_sprzedazy>2,3762</kurs_sprzedazy>\n"
                + "</pozycja>\n"
                + "</tabela_kursow>"));

        // WHEN
        List<CurrencyRates> currencyRates = ratesDownloader
            .getCurrencyRates(date, date, CurrencyCode.USD, TableType.BUY_SELL_RATES).toBlocking().single();

        // THEN
        assertThat(currencyRates).hasSize(2);
        assertThat(currencyRates.get(0).getCurrencyCode()).isEqualTo(CurrencyCode.USD);
        assertThat(currencyRates.get(0).getBuyPrice()).isEqualTo(2.8210);
        assertThat(currencyRates.get(0).getSellPrice()).isEqualTo(2.8780);
        assertThat(currencyRates.get(0).getPublicationDate()).isEqualTo(LocalDate.of(2007,4,12));

        assertThat(currencyRates.get(1).getCurrencyCode()).isEqualTo(CurrencyCode.USD);
        assertThat(currencyRates.get(1).getBuyPrice()).isEqualTo(2.3292);
        assertThat(currencyRates.get(1).getSellPrice()).isEqualTo(2.3762);
        assertThat(currencyRates.get(1).getPublicationDate()).isEqualTo(LocalDate.of(2007,4,13));
    }
}
