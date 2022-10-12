package com.backend.chain.response;

import lombok.Data;

@Data
public class Response {
    private int code;
    private String message;
    private Object result;

    Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }
}