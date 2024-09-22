package com.example.spring_demo;

import com.example.spring_demo.currency.CurrencyRepository;
import com.example.spring_demo.dailyExchange.DailyExchangeRateService;
import com.example.spring_demo.dailyExchange.DailyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SpringDemoApplication implements CommandLineRunner {

	@Autowired
	private DailyExchangeRateService dailyExchangeRateService;

	@Autowired
	private DailyExchangeRepository dailyExchangeRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	public static void main(String[] args) {

		SpringApplication.run(SpringDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		dailyExchangeRepository.deleteAll();
//		dailyExchangeRateService.fetchAndStoreCurrencyData();

	}

}
