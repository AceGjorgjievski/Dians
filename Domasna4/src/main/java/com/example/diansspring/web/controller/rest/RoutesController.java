package com.example.diansspring.web.controller.rest;

import com.example.diansspring.service.configuration.ConfigurationService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/routes")
public class RoutesController {
    private final ConfigurationService configurationService;

    public RoutesController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/getData")
    public String getRouteData(@RequestParam String point1, @RequestParam String point2) {
        return callGraphhopperApi(point1, point2);
    }

    private String callGraphhopperApi(String point1, String point2) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = new RestTemplate().exchange(
                "https://graphhopper.com/api/1/route?key=" + this.configurationService.getGraphhopperApiKey() + "&vehicle=foot&points_encoded=false&point=" + point1 + "&point=" + point2,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        return response.getBody();
    }
}
