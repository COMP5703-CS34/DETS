package com.backend.chain.controller;

import com.backend.chain.service.HyperledgerService;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.utility.Utility;
import org.hyperledger.fabric.chaincode.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonObject;

/**
 * User Information Controller
 *
 * @author Yiwei Xu
 * @date 2022/09
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserInfoController {

    @Autowired
    HyperledgerService hyperledgerService;

    @GetMapping("/api/userInfo/{name}")
    public Response getUserInfo(@PathVariable("name") String name) {
        byte[] bytes = hyperledgerService.getUserInfo(name);
        Account resAccount = (Account) Utility.toObject(bytes);

        return ResponseFactory.buildSuccessResult(resAccount);
    }
}
