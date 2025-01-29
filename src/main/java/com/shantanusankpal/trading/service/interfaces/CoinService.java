package com.shantanusankpal.trading.service.interfaces;

import com.shantanusankpal.trading.dto.Coin;

import java.util.List;

public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String CoinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50ByMarketCap() throws Exception;

    String getTradingPoints() throws Exception;
}
