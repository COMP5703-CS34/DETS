package com.backend.chain.response;

public class ResponseFactory {
    public static Response buildSuccessResult(Object data) {
        return buildResult(ResponseCode.SUCCESS, "Success", data);
    }

    public static Response buildFailResult(String message) {
        return buildResult(ResponseCode.FAIL, message, null);
    }

    public static Response buildResult(ResponseCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    public static Response buildResult(int resultCode, String message, Object data) {
        return new Response(resultCode, message, data);
    }
}
