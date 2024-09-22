package com.example.spring_demo.currency;

import com.example.spring_demo.dailyExchange.DailyExchangeRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CurrencyRepository extends MongoRepository<DailyExchangeRate, String> {
    @Query("{'date': {'$gte': ?0, '$lte': ?1}}")
    List<DailyExchangeRate> findByDateBetweenAndCurrency(String startDate, String endDate, String currency);

    List<DailyExchangeRate> findByCurrency(String currency);
}
