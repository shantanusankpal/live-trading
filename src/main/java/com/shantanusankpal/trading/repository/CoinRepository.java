package com.shantanusankpal.trading.repository;

import com.shantanusankpal.trading.dto.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,String > {
}
