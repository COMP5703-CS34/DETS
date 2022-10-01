package com.backend.chain.controller;

import com.backend.chain.service.HyperledgerService;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User Information Controller
 *
 * @author Zhiheng Chen
 * @date 2022/09
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

    @Autowired
    HyperledgerService hyperledgerService;

    @PostMapping("/api/login")
    public Response login(@RequestParam("name") String name,
                          @RequestParam("InputPwd") String inputPwd) {
        byte[] bytes = hyperledgerService.login(name);
        String realPwd = new String(bytes);

        if (inputPwd.equals(realPwd)) {
            return ResponseFactory.buildSuccessResult(true);
        }

        return ResponseFactory.buildSuccessResult(false);
    }
}
