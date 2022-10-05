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

    @PostMapping("/api/userControl/add/")
    public Response Add(@RequestParam("name") String accountID,
                        @RequestParam("elecAmount") String elecAmount,
                        @RequestParam("balance") String balance,
                        @RequestParam("password") String password,
                        @RequestParam("identity") String identity,
                        @RequestParam("adminID") String adminID) {

        byte[] bytes = hyperledgerService.addUser(accountID, elecAmount, balance, password, identity, adminID);

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }

    @PostMapping("/api/userControl/update/")
    public Response Update(@RequestParam("name") String accountID,
                           @RequestParam("elecAmount") String elecAmount,
                           @RequestParam("balance") String balance,
                           @RequestParam("password") String password,
                           @RequestParam("identity") String identity) {
        byte[] bytes = hyperledgerService.updateUser(accountID, elecAmount, balance, password, identity);

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }

    @GetMapping("/api/userControl/delete/{name}")
    public Response Delete(@PathVariable("name") String name) {

        byte[] bytes = hyperledgerService.deleteUser(name);

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }


}
