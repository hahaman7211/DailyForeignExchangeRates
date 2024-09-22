package com.example.spring_demo.dailyExchange;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DailyExchangeScheduler {

    private final DailyExchangeRateService dailyExchangeRateService;

    public DailyExchangeScheduler(DailyExchangeRateService dailyExchangeRateService) {
        this.dailyExchangeRateService = dailyExchangeRateService;
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void dailyExchangeRateTask() {
        dailyExchangeRateService.fetchAndStoreCurrencyData();
    }
}
