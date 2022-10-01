package com.backend.chain.controller;


import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.service.HyperledgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller
 *
 * @author Zhiheng Chen
 * @date 2022/09
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    HyperledgerService hyperledgerService;

    @PostMapping("/api/add/Account")
    public Response Add(@RequestParam("name") String accountID,
                        @RequestParam("elecAmount") String elecAmount,
                        @RequestParam("balance") String balance,
                        @RequestParam("password") String password) {

        byte[] bytes = hyperledgerService.add(accountID, elecAmount, balance, password);

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }

    @PostMapping("/api/delete/{name}")
    public Response Delete(@RequestParam("name") String name) {

        byte[] bytes = hyperledgerService.delete(name);

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }

}
