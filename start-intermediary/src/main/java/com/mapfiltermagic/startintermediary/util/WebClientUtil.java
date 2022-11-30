package com.mapfiltermagic.startintermediary.util;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * Holds all relevant methods for instantiating a fully configured WebClient.
 */
public final class WebClientUtil {
    
    private WebClientUtil() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    // TODO: Rewrite to take in timeout values
    /**
     *
     * @param baseURI
     * @return
     */
    public static WebClient getWebClient(String baseUrl) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .baseUrl(baseUrl)
                .filter(new LoggingExchangeFilterFunction(baseUrl))
                .build();
    }

    // TODO: Rewrite to take in timeout values
    /**
     *
     * @return
     */
    private static HttpClient getHttpClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000)
                .responseTimeout(Duration.ofMillis(15000))
                .doOnConnected(conn -> 
                        conn.addHandlerLast(new ReadTimeoutHandler(15000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(15000, TimeUnit.MILLISECONDS))
                );

        return httpClient;
    }

}
