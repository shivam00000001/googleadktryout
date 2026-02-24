package com.learning.adk.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "https://wttr.in")
public interface WeatherClient {

    @GetMapping("/{city}")
    String getLiveWeather(
            @PathVariable("city") String city,
            @RequestParam("format") String format
    );
}
