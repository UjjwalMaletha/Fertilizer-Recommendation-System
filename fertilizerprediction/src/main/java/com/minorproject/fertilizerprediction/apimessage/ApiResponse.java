package com.minorproject.fertilizerprediction.apimessage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private T data;
    private String message;
    private boolean success;


}
