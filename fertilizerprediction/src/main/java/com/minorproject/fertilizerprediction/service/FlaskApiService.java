package com.minorproject.fertilizerprediction.service;

import com.minorproject.fertilizerprediction.apimessage.ApiResponse;
import com.minorproject.fertilizerprediction.exceptions.CustomException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class FlaskApiService {

    private final RestTemplate restTemplate;

    public FlaskApiService() {
        this.restTemplate = new RestTemplate();
    }

    public ApiResponse<Map<String, Object>> callFlaskApi(Map<String, String> inputData) {
        String url = "http://127.0.0.1:5000/predict";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(inputData);
        } catch (Exception e) {
            throw new CustomException("Error converting input data to JSON: " + e.getMessage());
        }

        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            return new ApiResponse<>(response.getBody(), "Success", true);
        } catch (HttpClientErrorException e) {
            HttpStatus status = (HttpStatus) e.getStatusCode();
            String errorMessage = parseErrorResponse(e.getResponseBodyAsString());
            throw new CustomException(errorMessage,status);
        } catch (Exception e) {
            throw new CustomException("Error calling Flask API: " + e.getMessage());
        }
    }

    private String parseErrorResponse(String response){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response, Map.class);
            return map.get("error").toString();
        }
        catch (Exception e){
            return response;
        }
    }
}
