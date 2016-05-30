package pl.parser.nbp.http;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import rx.Observable;

import java.nio.charset.StandardCharsets;

/**
 * Created by Krzysztof Pawlowski on 28/05/16.
 */
public class AsyncHttpClient {

    private static final int DEFAULT_PORT =  80;

    private HttpClient<ByteBuf, ByteBuf> httpClient;

    public AsyncHttpClient(String host, int port) {
        httpClient = RxNetty.createHttpClient(host, port);
    }

    public AsyncHttpClient(String host) {
        this(host, DEFAULT_PORT);
    }

    public Observable<String> performGetRequest(String path) {
        return httpClient.submit(HttpClientRequest
            .createGet(path))
            .flatMap(byteBufHttpClientResponse -> byteBufHttpClientResponse.getContent())
            .map(byteBuf -> byteBuf.toString(StandardCharsets.UTF_8));
    }

    public void close() {
        httpClient.shutdown();
    }
}
