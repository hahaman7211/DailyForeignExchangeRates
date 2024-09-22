package com.example.spring_demo.currency;

import com.example.spring_demo.dailyExchange.DailyExchangeRate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;
    private final ObjectMapper objectMapper;

    public CurrencyController(CurrencyService currencyService, ObjectMapper objectMapper) {
        this.currencyService = currencyService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/currency")
    public ResponseEntity<JsonNode> getCurrencyHistory(@RequestBody CurrencyRequest request) throws ParseException {
        List<DailyExchangeRate> list = null;
        if (validateDate(request.getStartDate(), request.getEndDate())) {
            String startDate = convertDate(request.getStartDate());
            String endDate = convertDate(request.getEndDate());
            String currency = request.getCurrency();
            try {
                list = currencyService.getHistoricalData(startDate, endDate, currency);
            }
            catch (Exception e) {
            }
        }
        else {
            return createErrorResponse();
        }
        return createSuccessResponse(list, request.getCurrency());
    }

    private ResponseEntity<JsonNode> createSuccessResponse(List<DailyExchangeRate> list, String currency) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode("0000");
        errorInfo.setMessage("成功");
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.putPOJO("error", errorInfo);
        ArrayNode currencyArray = responseNode.putArray("currency");
        list.forEach(rate -> currencyArray.addObject()
                        .put("date", rate.getDate())
                        .put(currency, rate.getRate())
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseNode);
    }

    private ResponseEntity<JsonNode> createErrorResponse() {
        try {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setCode("E001");
            errorInfo.setMessage("日期區間不符");
            ObjectNode responseNode = objectMapper.createObjectNode();
            responseNode.putPOJO("error", errorInfo);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseNode);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public boolean validateDate(String startDateStr, String endDateStr) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);
            LocalDate today = LocalDate.now();
            LocalDate oneYearAgo = today.minusYears(1).minusDays(1);

            return (startDate.isBefore(today) && startDate.isAfter(oneYearAgo)) &&
                    endDate.isBefore(today) &&
                    (endDate.isAfter(startDate) || endDate.isEqual(startDate));
        }
        catch (DateTimeParseException e) {
            return false;
        }

    }

    public String convertDate(String inputDate) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");
        String date = "";
        try {
            date = outputFormat.format(inputFormat.parse(inputDate));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
