package com.example.tournamentservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ServiceAPI {
    @Autowired
    private  RestTemplate restTemplate;

    public <T> T call(String urlService, HttpMethod method, Object requestBody, Class<T> responseType) {
        System.out.println("call to " + urlService + " " + method + " " + requestBody);
        try {
            HttpEntity<?> httpEntity = requestBody != null ? new HttpEntity<>(requestBody) : new HttpEntity<>(null);
            ResponseEntity<T> response = restTemplate.exchange(urlService, method, httpEntity, responseType);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to call service: " + urlService + ", error: " + e.getMessage());
        }
    }
}
