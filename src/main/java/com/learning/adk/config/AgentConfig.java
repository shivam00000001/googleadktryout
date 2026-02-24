package com.learning.adk.config;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import com.learning.adk.tool.WeatherTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
public class AgentConfig {

    @Value("${gemini.agent.model:gemini-2.5-flash}")
    String modelName;

    @Bean
    public LlmAgent mainAgent(WeatherTool weatherTool) {

        // 1. Create the sub-agent and wrap the Spring bean method as a tool
        LlmAgent weatherSubAgent = LlmAgent.builder()
                .name("weather_specialist")
                .description("Handles all weather-related queries.")
                .model(modelName)
                .instruction("You are a weather specialist. Use the getWeather tool to answer weather questions.")
                .tools(List.of(FunctionTool.create(weatherTool, "getWeather")))
                .build();

        // 2. Create and return the root agent, attaching the sub-agent
        return LlmAgent.builder()
                .name("general_assistant")
                .description("Primary assistant that routes specific tasks.")
                .model(modelName)
                .instruction("You are a helpful assistant. If the user asks for the weather, delegate the request to the weather_specialist.")
                .subAgents(List.of(weatherSubAgent))
                .build();
    }
}
