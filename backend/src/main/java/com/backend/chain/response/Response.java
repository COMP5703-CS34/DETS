package com.backend.chain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private int code;
    private String message;
    private Object result;

    public static Response success(){
        Response response=new Response();
        response.setCode(0);
        response.setMessage("success");
        return response;
    }
    public static Response success(Object object){
        Response response=new Response();
        response.setCode(0);
        response.setMessage("success");
        response.setResult(object);
        return response;
    }
}
