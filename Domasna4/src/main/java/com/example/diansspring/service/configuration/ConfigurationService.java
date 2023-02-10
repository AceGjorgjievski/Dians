package com.example.diansspring.service.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class ConfigurationService {
    @Value("${BASE_URL}")
    private String baseUrl;

    @Value("${GRAPHHOPPER_API_KEY}")
    private String graphhopperApiKey;
}