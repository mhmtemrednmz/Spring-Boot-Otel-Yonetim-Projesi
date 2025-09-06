package com.emrednmz.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootEntity <T> {
    private int status;
    private T payload;
    private String errorMessage;


    public static <T> RootEntity <T> ok(T payload) {
        RootEntity <T> root = new RootEntity <T>();
        root.setStatus(200);
        root.setPayload(payload);
        root.setErrorMessage(null);
        return root;
    }

    public static <T> RootEntity <T> error(String errorMessage) {
        RootEntity <T> root = new RootEntity <T>();
        root.setStatus(500);
        root.setErrorMessage(errorMessage);
        root.setPayload(null);
        return root;
    }
}
