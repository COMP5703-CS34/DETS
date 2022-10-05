package com.backend.chain.controller;

import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.service.HyperledgerService;
import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.chaincode.Account;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Transaction Controller
 *
 * @author Yiwei Xu
 * @date 2022/09
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
    @Autowired
    HyperledgerService hyperledgerService;

    @PostMapping("/api/transaction/transfer")
    public Response doTransaction(@RequestParam("toAccount") String toAccount,
                                  @RequestParam("fromAccount") String fromAccount,
                                  @RequestParam("elecAmount") String elecAmount,
                                  @RequestParam("elecPrice") String elecPrice) {

        byte[] bytes = hyperledgerService.makeTransfer(toAccount, fromAccount, elecAmount, elecPrice);
        if (bytes == null) {
            return ResponseFactory.buildFailResult("Illegal Transaction incurred!");
        }

        return ResponseFactory.buildSuccessResult(new String(bytes));
    }

    @GetMapping("/api/transaction/queryall/{name}")
    public Response getAllUser(@PathVariable("name") String name) {

        List<Account> allUsers = new ArrayList<>();
        byte[] bytes = hyperledgerService.getAllUser();

        String toBeProcessed = new String(bytes);
        JSONObject jsonObject = new JSONObject(toBeProcessed);
        JSONArray jsonArray = jsonObject.getJSONArray("accounts").getJSONArray(0);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject accountJsonObject = jsonArray.getJSONObject(i);
            String accountId = accountJsonObject.getString("accountId");
            if (accountId.equals(name)) continue;
            Double elecAmount = accountJsonObject.getDouble("elecAmount");
            Double balance = accountJsonObject.getDouble("balance");
            String identity = accountJsonObject.getString("identity");
            Account account = new Account(accountId, elecAmount, balance, "Not Showing!", identity);
            allUsers.add(account);
        }

        return ResponseFactory.buildSuccessResult(allUsers);
    }
}
