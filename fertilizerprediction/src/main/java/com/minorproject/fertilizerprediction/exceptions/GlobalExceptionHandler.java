package com.minorproject.fertilizerprediction.exceptions;

import com.minorproject.fertilizerprediction.apimessage.ApiResponse;
import com.minorproject.fertilizerprediction.apimessage.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleHttpClientErrorException(HttpClientErrorException ex){
        ErrorResponse error = new ErrorResponse("Error", ex.getResponseBodyAsString());
        HttpStatusCode status = ex.getStatusCode();
        ApiResponse<ErrorResponse> response = new ApiResponse<>(error, "Failure", false);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception ex){
        ErrorResponse error = new ErrorResponse("Server Error", ex.getMessage());
        ApiResponse<ErrorResponse> response = new ApiResponse<>(error, "Failure", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequest(HttpClientErrorException ex){
    ErrorResponse error = new ErrorResponse("Bad Request", ex.getResponseBodyAsString());
    ApiResponse<ErrorResponse> response = new ApiResponse<>(error, "Failure", false);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class) public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomException(CustomException ex){
        ErrorResponse error = new ErrorResponse("Custom Error", ex.getMessage());
        ApiResponse<ErrorResponse> response = new ApiResponse<>(error, "Failure", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
