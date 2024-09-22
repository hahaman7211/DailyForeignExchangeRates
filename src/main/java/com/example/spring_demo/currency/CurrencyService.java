package com.example.spring_demo.currency;

import com.example.spring_demo.dailyExchange.DailyExchangeRate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<DailyExchangeRate> getHistoricalData(String startDate, String endDate, String usd) {
        return currencyRepository.findByDateBetweenAndCurrency(startDate, endDate, usd);

    }
}
