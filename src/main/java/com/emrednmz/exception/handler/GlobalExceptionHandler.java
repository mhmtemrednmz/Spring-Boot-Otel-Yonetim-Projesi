package com.emrednmz.exception.handler;

import com.emrednmz.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMessage(), request));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        for (ObjectError objError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError)objError).getField();

            if(errors.containsKey(fieldName)) {
                errors.put(fieldName, addValue(errors.get(fieldName), objError.getDefaultMessage()));
            } else {
                errors.put(fieldName, addValue(new ArrayList<>(), objError.getDefaultMessage()));
            }
        }
        return ResponseEntity.badRequest().body(createApiError(errors, request));
    }



    public <E> ApiError<E> createApiError(E message, WebRequest request) {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Exception<E> exception = new Exception<>();
        exception.setMessage(message);
        exception.setPath(request.getDescription(false).substring(4));
        exception.setTimestamp(new Date());
        exception.setHostname(getHostName());

        apiError.setMessage(exception);
        return apiError;
    }

    private String getHostName(){
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        return "";
    }

    private List<String> addValue(List<String> list, String value){
        list.add(value);
        return list;
    }
}
