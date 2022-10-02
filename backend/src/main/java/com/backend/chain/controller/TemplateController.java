package com.backend.chain.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TemplateController {
    //http://localhost:3000/api/test
    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object>  hello(HttpServletRequest request,HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "World");
        map.put("msg", "Hello");
        return map;
    }
}