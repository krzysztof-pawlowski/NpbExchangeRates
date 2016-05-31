package pl.parser.nbp.http;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import rx.Observable;

import java.nio.charset.StandardCharsets;

/**
 * Asynchronous http client.
 */
public class AsyncHttpClient {

    private static final int DEFAULT_PORT =  80;

    private HttpClient<ByteBuf, ByteBuf> httpClient;

    /**
     * The constructor.
     * @param host host address
     * @param port host port
     */
    public AsyncHttpClient(String host, int port) {
        httpClient = RxNetty.createHttpClient(host, port);
    }

    /**
     * The constructor.
     * @param host host address
     */
    public AsyncHttpClient(String host) {
        this(host, DEFAULT_PORT);
    }

    /**
     * Performs http get request and returns response body as a @String.
     * @param path get request path
     * @return response body
     */
    public Observable<String> performGetRequest(String path) {
        return httpClient.submit(HttpClientRequest
            .createGet(path))
            .flatMap(byteBufHttpClientResponse -> byteBufHttpClientResponse.getContent())
            .map(byteBuf -> byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * Closes the http client.
     */
    public void close() {
        httpClient.shutdown();
    }
}
