package com.learning.adk.config;import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
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
    public LlmAgent mainAgent(WeatherTool weatherTool) {

        // FIX: Stronger instructions forbidding reverse-routing
        LlmAgent weatherSubAgent = LlmAgent.builder()
                .name("weather_specialist")
                .description("Handles all weather and local time queries.")
                .model(modelName)
                .instruction("You are a weather and time specialist. You MUST use the getWeather tool to extract the city and answer queries about weather or time. NEVER transfer the task back to the general_assistant.")
                .tools(List.of(FunctionTool.create(weatherTool, "getWeather")))
                .build();

        // FIX: Clarify that the sub-agent handles time as well
        return LlmAgent.builder()
                .name("general_assistant")
                .description("Primary assistant that routes specific tasks.")
                .model(modelName)
                .instruction("You are a helpful assistant. If the user asks for the weather OR the current time in a city, delegate the request to the weather_specialist.")
                .subAgents(List.of(weatherSubAgent))
                .build();
    }
}