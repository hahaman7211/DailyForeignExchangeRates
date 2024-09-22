package com.example.spring_demo.dailyExchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class DailyExchangeRateService {

    private final RestTemplate restTemplate;
    private final DailyExchangeRepository dailyExchangeRepository;

    @Value("${daily.exchange.rate.url}")
    private String url;

    public DailyExchangeRateService(RestTemplate restTemplate, DailyExchangeRepository dailyExchangeRepository) {
        this.restTemplate = restTemplate;
        this.dailyExchangeRepository = dailyExchangeRepository;
    }

    public void fetchAndStoreCurrencyData() {
        try {
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonArray = mapper.readTree(json);

            if (jsonArray.isArray()) {
                for (JsonNode jsonNode : jsonArray) {
                    DailyExchangeRate usdRates = new DailyExchangeRate();
                    String dateStr = jsonNode.get("Date").asText();
                    double rate = jsonNode.get("USD/NTD").asDouble();
                    usdRates.setDate(dateStr);
                    usdRates.setCurrency("usd");
                    usdRates.setRate(rate);
                    dailyExchangeRepository.save(usdRates);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DailyExchangeRate> getDailyExchangeRate() {
        return dailyExchangeRepository.findAll();
    }

}
