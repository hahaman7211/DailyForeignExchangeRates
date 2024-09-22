package com.example.spring_demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configapp {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
