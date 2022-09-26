package com.backend.chain.controller;

import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.service.HyperledgerService;
import com.backend.chain.utility.Utility;
import org.hyperledger.fabric.chaincode.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
    @Autowired
    HyperledgerService hyperledgerService;

    @PostMapping("/api/doTransaction")
    public Response getUserInfo(@RequestParam("toAccount") String toAccount,
                                @RequestParam("fromAccount") String fromAccount,
                                @RequestParam("elecAmount") String elecAmount,
                                @RequestParam("elecPrice") String elecPrice) {

        byte[] bytes = hyperledgerService.makeTransfer(toAccount, fromAccount, elecAmount, elecPrice);

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }
}
