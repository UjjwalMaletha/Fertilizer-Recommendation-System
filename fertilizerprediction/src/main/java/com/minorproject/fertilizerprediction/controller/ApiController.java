package com.minorproject.fertilizerprediction.controller;
import com.minorproject.fertilizerprediction.apimessage.ApiResponse;
import com.minorproject.fertilizerprediction.service.FlaskApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private FlaskApiService flaskApiService;

    @PostMapping("/api/predict")
    public ApiResponse<Map<String, Object>> getPrediction(@RequestBody Map<String, String> inputData) {
        return flaskApiService.callFlaskApi(inputData);
    }
}



