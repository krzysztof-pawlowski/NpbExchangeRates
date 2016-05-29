package pl.parser.nbp.downloader;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class AsyncHttpClientTest {

    @Rule
    public WireMockRule service1 = new WireMockRule(8767);

    private AsyncHttpClient asyncHttpClient;
    private String responseBody = "Sample response body";

    @Before
    public void before() {
        asyncHttpClient = new AsyncHttpClient("localhost", 8767);


        stubFor(get(urlEqualTo("/body"))
            .willReturn(aResponse()
                .withBody(responseBody)));
    }

    @After
    public void after() {
        asyncHttpClient.close();
    }

    @Test
    public void performGetRequestTest() throws ExecutionException, InterruptedException {
        // WHEN
        String response = asyncHttpClient.performGetRequest("/body").toBlocking().last();

        // THEN
        assertThat(response).isEqualTo(responseBody);

    }

}