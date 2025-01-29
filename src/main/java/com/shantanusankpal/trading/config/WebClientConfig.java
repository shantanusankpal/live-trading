package com.shantanusankpal.trading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("$webclient.base.url.coingecko")
    String coinGeckoBaseUrl;

    @Bean
    public WebClient coinGeckoClient(WebClient.Builder builder) {
        return builder.baseUrl(coinGeckoBaseUrl).build();
    }

    @Bean
    public WebClient xyzWebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://xyz.com").build();
    }
}
