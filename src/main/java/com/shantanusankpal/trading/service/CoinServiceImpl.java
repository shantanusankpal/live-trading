package com.shantanusankpal.trading.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanusankpal.trading.dto.Coin;
import com.shantanusankpal.trading.repository.CoinRepository;
import com.shantanusankpal.trading.service.interfaces.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    private final WebClient coinGeckoClient;


    public CoinServiceImpl(@Qualifier("coinGeckoClient") WebClient coinGeckoClient) {
        this.coinGeckoClient = coinGeckoClient;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) throws Exception {

        try {
            String response = coinGeckoClient.get()
                    .uri("/coins/markets?vs_currency=inr&x_cg_demo_api_key=CG-PJwu2pfK3Dt9r5rfLDAEcu9U&per_page=10&page="+page) // Full URL instead of baseUrl
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            List<Coin> coins = objectMapper.readValue(response, new TypeReference<List<Coin>>() {
            });

            return coins;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        try {
            String response = coinGeckoClient.get()
                    .uri("/coins/"+coinId+"/market_chart?vs_currency=inr&x_cg_demo_api_key=CG-PJwu2pfK3Dt9r5rfLDAEcu9U&days="+days) // Full URL instead of baseUrl
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getCoinDetails(String coinId) throws Exception {
        try {
            String response = coinGeckoClient.get()
                    .uri("/coins/"+coinId+"/?x_cg_demo_api_key=CG-PJwu2pfK3Dt9r5rfLDAEcu9U") // Full URL instead of baseUrl
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);

            Coin coin = new Coin();

            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setImage(jsonNode.get("image").get("Large").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());

            JsonNode marketData = jsonNode.get("market_data");

            coin.setCurrentPrice(marketData.get("current_price").get("inr").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("inr").asLong());
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("inr").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("inr").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("inr").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asLong());
            coin.setTotalSupply(marketData.get("total_supply").asLong());

            coinRepository.saveAndFlush(coin);
            return response;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Coin findById(String coinId) throws Exception {

        Optional<Coin> coin = coinRepository.findById(coinId);

        if(coin.isEmpty()) throw new Exception("Coin Not found");

        return coin.get();
    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        try {
            String response = coinGeckoClient.get()
                    .uri("/search?query="+keyword+"&x_cg_demo_api_key=CG-PJwu2pfK3Dt9r5rfLDAEcu9U") // Full URL instead of baseUrl
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTop50ByMarketCap() throws Exception {
        try {
            String response = coinGeckoClient.get()
                    .uri("/coins/markets?vs_currency=inr&x_cg_demo_api_key=CG-PJwu2pfK3Dt9r5rfLDAEcu9U&per_page=50&page=1") // Full URL instead of baseUrl
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTradingPoints() throws Exception {
        try {
            String response = coinGeckoClient.get()
                    .uri("/search/trending?x_cg_demo_api_key=CG-PJwu2pfK3Dt9r5rfLDAEcu9U") // Full URL instead of baseUrl
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
}
