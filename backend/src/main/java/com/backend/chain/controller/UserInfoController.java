package com.backend.chain.controller;

import com.backend.chain.service.HyperledgerService;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Information Controller
 *
 * @author Yiwei Xu
 * @date 2022/09
 */

@RestController
public class UserInfoController {

    @Autowired
    HyperledgerService hyperledgerService;

    @GetMapping("/api/userInfo/userInfo")
    public Response getUserInfo(String name) {
        hyperledgerService.getUserInfo(name);
        return ResponseFactory.buildSuccessResult("Get User Info Success!");
    }
}
