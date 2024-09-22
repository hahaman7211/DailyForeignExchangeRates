package com.example.spring_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SpringDemoApplication {

//	@Autowired
//	private DailyExchangeRateService dailyExchangeRateService;
//
//	@Autowired
//	private DailyExchangeRepository dailyExchangeRepository;
//
//	@Autowired
//	private CurrencyRepository currencyRepository;

	public static void main(String[] args) {

		SpringApplication.run(SpringDemoApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
////		dailyExchangeRepository.deleteAll();
////		dailyExchangeRateService.fetchAndStoreCurrencyData();
//
//	}

}
