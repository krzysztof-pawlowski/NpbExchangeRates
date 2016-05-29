package pl.parser.nbp.downloader;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class RatesDownloader {

    static final String RATES_FILE_URL_PREFIX = "http://www.nbp.pl/kursy/xml/";

    private AsyncHttpClient asyncHttpClient;

    public RatesDownloader() {
        this(null);
    }

    public RatesDownloader(AsyncHttpClient asyncHttpClient) {
        if (asyncHttpClient == null) {
            this.asyncHttpClient = new AsyncHttpClient(RATES_FILE_URL_PREFIX);
        } else {
            this.asyncHttpClient = asyncHttpClient;
        }
    }


}
