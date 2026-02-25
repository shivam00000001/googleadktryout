package com.learning.adk.config;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import com.learning.adk.tool.FinanceTool;
import com.learning.adk.tool.WeatherTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AgentConfig {

    @Value("${gemini.agent.model:gemini-2.5-flash-lite}")
    private String modelName;

    @Bean
    public LlmAgent mainAgent(WeatherTool weatherTool, FinanceTool financeTool) {

        LlmAgent weatherSubAgent = LlmAgent.builder()
                .name("weather_specialist")
                .description("Handles all weather and local time queries.")
                .model(modelName)
                .instruction("You are a weather and time specialist. You MUST use the getWeather tool to extract the city and answer queries about weather or time. NEVER transfer the task back to the general_assistant.")
                .tools(List.of(FunctionTool.create(weatherTool, "getWeather")))
                .build();

        // UPDATED: Finance Agent Instructions for both crypto and traditional stocks
        LlmAgent financeSubAgent = LlmAgent.builder()
                .name("finance_specialist")
                .description("Handles cryptocurrency, stock market queries, and financial suggestions.")
                .model(modelName)
                .instruction("You are a financial specialist. You can suggest and verify stocks or crypto using your general knowledge. " +
                        "To get live prices, use the getPrice tool. You MUST convert natural language (e.g., 'Apple', 'Bitcoin') " +
                        "into official Yahoo Finance tickers (e.g., 'AAPL', 'BTC-USD') before calling the tool. " +
                        "NEVER transfer back to the general_assistant.")
                .tools(List.of(FunctionTool.create(financeTool, "getPrice")))
                .build();

        return LlmAgent.builder()
                .name("general_assistant")
                .description("Primary assistant that routes specific tasks.")
                .model(modelName)
                .instruction("You are a helpful assistant. " +
                        "If the user asks for weather or time, transfer to weather_specialist. " +
                        "If the user asks for crypto, stock prices, or financial suggestions, transfer to finance_specialist. " +
                        "Otherwise, answer the question yourself.")
                .subAgents(List.of(weatherSubAgent, financeSubAgent))
                .build();
    }
}