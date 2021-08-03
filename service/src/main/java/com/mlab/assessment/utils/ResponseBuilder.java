package com.mlab.assessment.utils;

import com.mlab.assessment.model.ApiResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@UtilityClass
public class ResponseBuilder {

    public <T> ApiResponse<T> buildResponse(T item, HttpStatus status, String message){

        return ApiResponse.<T>builder()
                .responseCode(status.value())
                .responseMessage(message)
                .data(item)
                .build();
    }

    public <T> ApiResponse<T> buildResponse(T item, HttpStatus status){
        return buildResponse(item, status, status.getReasonPhrase());
    }

    public ApiResponse<Void> buildResponse(HttpStatus status){
        return buildResponse(null, status);
    }

    public ApiResponse<Void> buildResponse(HttpStatus status, String message){
        return buildResponse(null, status, message);
    }

    public <T> ApiResponse<T> buildOkResponse(T item, String message){
        return buildResponse(item, HttpStatus.OK, message);
    }

    public <T> ApiResponse<T> buildOkResponse(T item){
        return buildResponse(item, HttpStatus.OK);
    }

    public ApiResponse<Void> buildOkResponse(){
        return buildResponse(HttpStatus.OK);
    }

}
