package com.learning.adk.tool;

import com.learning.adk.client.WeatherClient;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    private final WeatherClient weatherClient;

    // Spring automatically injects the Feign client here
    public WeatherTool(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public String getWeather(String city) {
        try {
            // Define the custom string format for the API to return
            String customFormat = "Location: %l | Weather: %C %t | Local Time: %T";

            // Execute the single API call
            return weatherClient.getLiveWeather(city, customFormat);

        } catch (Exception e) {
            // Give the AI a direct instruction instead of a pre-written apology
            return "SYSTEM ERROR: The external weather API timed out or failed. " +
                    "Instruct the agent to apologize to the user and state that " +
                    "the live weather service is temporarily unavailable.";
        }
    }
}