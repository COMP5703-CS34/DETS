package com.backend.chain.controller;

import com.backend.chain.service.HyperledgerService;
import com.backend.chain.utility.Utility;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;

import org.hyperledger.fabric.chaincode.Account;
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
        byte[] pwdBytes = hyperledgerService.login(name);
        String realPwd = new String(pwdBytes);

        byte[] accountBytes = hyperledgerService.getUserInfo(name);
        Account account = (Account) Utility.toObject(accountBytes);

        if (inputPwd.equals(realPwd)) {
            return ResponseFactory.buildSuccessResult(account.getIdentity());
        }

        return ResponseFactory.buildFailResult("Invalid password or account");
    }
}
