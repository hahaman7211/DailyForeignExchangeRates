package com.example.spring_demo;


import com.example.spring_demo.currency.CurrencyRequest;
import com.example.spring_demo.dailyExchange.DailyExchangeRate;
import com.example.spring_demo.dailyExchange.DailyExchangeRateService;
import com.example.spring_demo.dailyExchange.DailyExchangeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SpringDemoApplicationTests {

	@Autowired
	private DailyExchangeRateService dailyExchangeRateService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testDailyExchangeRate() {
		dailyExchangeRateService.fetchAndStoreCurrencyData();
		DailyExchangeRate rate = dailyExchangeRateService.getDailyExchangeRate().get(0);
		Assertions.assertNotNull(rate);
	}

	@Test
	@SneakyThrows
	public void testForexApi() {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.readTree(new File("src/test/resources/request.json"));

		mockMvc.perform(post("/currency")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(node)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.error.code").value("0000"))
				.andExpect(jsonPath("$.error.message").value("成功"))
				.andExpect(jsonPath("$.currency[0].date").value("20240801"));
	}

	@Test
	@SneakyThrows
	public void testDateFormat() {
		ObjectMapper objectMapper = new ObjectMapper();
		CurrencyRequest request = new CurrencyRequest();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String today = localDate.format(formatter);

		request.setStartDate("2024/08/05");
		request.setEndDate(today);
		request.setCurrency("usd");

		mockMvc.perform(post("/currency")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.code").value("E001"))
				.andExpect(jsonPath("$.error.message").value("日期區間不符"));
	}

}
