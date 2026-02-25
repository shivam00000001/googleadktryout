package com.learning.adk.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "financeClient", url = "https://query1.finance.yahoo.com/v8/finance")
public interface FinanceClient {

    @GetMapping(value = "/chart/{symbol}", headers = "User-Agent=Mozilla/5.0")
    String getQuote(@PathVariable("symbol") String symbol);
}