package com.ticker.binanceapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ticker.binanceapi.model.MarketDataModel;

@Repository
public interface MarketDataRepository extends JpaRepository<MarketDataModel, Long> {
}
