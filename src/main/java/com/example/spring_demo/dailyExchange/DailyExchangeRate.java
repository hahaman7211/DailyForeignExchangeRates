package com.example.spring_demo.dailyExchange;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "daily_exchange_rate")
public class DailyExchangeRate {

    @Id
    private String date;
    private String currency;
    private double rate;

    public DailyExchangeRate() {
    }

    public DailyExchangeRate(String date, String currency, double rate) {
        this.date = date;
        this.currency = currency;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
