package com.backend.chain.controller;

import com.backend.chain.service.HyperledgerService;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.utility.Utility;
import org.hyperledger.fabric.chaincode.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;

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

    @GetMapping("/api/login/{name}")
    public Response login(@RequestParam("name") String name,
                          @RequestParam("InputPwd") String inputPwd) {
        byte[] bytes = hyperledgerService.login(name);
        String realPwd = (String) Utility.toObject(bytes);

        if (inputPwd.equals(realPwd)) {
            return ResponseFactory.buildSuccessResult(true);
        }

        return ResponseFactory.buildSuccessResult(false);
    }
}
