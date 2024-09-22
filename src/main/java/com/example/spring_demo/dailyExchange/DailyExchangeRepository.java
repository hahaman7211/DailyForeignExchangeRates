package com.example.spring_demo.dailyExchange;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DailyExchangeRepository extends MongoRepository<DailyExchangeRate, String> {

}
