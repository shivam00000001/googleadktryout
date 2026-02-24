package com.learning.adk.tool;

import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    public String getWeather(String city) {
        // In the future, you could autowire a RestTemplate here to call a real Weather API
        return "The weather in " + city + " is sunny and 22°C.";
    }
}