package com.learning.adk.tool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.adk.client.FinanceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FinanceTool {

    private final FinanceClient financeClient;
    private final ObjectMapper objectMapper;

    // Spring automatically injects the ObjectMapper for JSON parsing
    public FinanceTool(FinanceClient financeClient, ObjectMapper objectMapper) {
        this.financeClient = financeClient;
        this.objectMapper = objectMapper;
    }

    public String getPrice(String tickerSymbol) {
        try {
            log.info("Executing FinanceTool for ticker: {}", tickerSymbol);
            String rawJson = financeClient.getQuote(tickerSymbol);

            // Navigate the v8 Chart JSON structure
            JsonNode meta = objectMapper.readTree(rawJson)
                    .path("chart")
                    .path("result")
                    .get(0)
                    .path("meta");

            if (meta == null || meta.isMissingNode()) {
                return "SYSTEM ERROR: Ticker symbol not found. Tell the user you cannot verify the stock or coin.";
            }

            // The chart endpoint uses 'symbol' instead of 'shortName'
            String name = meta.path("symbol").asText(tickerSymbol);
            double price = meta.path("regularMarketPrice").asDouble();
            String currency = meta.path("currency").asText("USD");

            return String.format("Asset: %s | Live Price: %.2f %s", name, price, currency);

        } catch (Exception e) {
            log.error("Finance API error", e);
            return "SYSTEM ERROR: The finance API is currently unavailable. Please inform the user gracefully.";
        }
    }
}