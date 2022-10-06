package com.backend.chain.controller;

import com.backend.chain.entity.Transation;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.service.HyperledgerService;
import java.util.ArrayList;
import java.util.List;

import com.backend.chain.service.TransationService;
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
    @Autowired
    TransationService transationService;

    @PostMapping("/api/transaction/transfer")
    public Response doTransaction(Transation req) {
        byte[] bytes = hyperledgerService.makeTransfer(req.getTo(), req.getFrom(), req.getAmount(), req.getPrice());
        transationService.addTransation(req);
        return ResponseFactory.buildSuccessResult(new String(bytes));
    }

    @GetMapping("/api/get/transaction/{userId}")
    public Response getAllByFromTransactions(@PathVariable("userId") String userId){
        Response response=new Response();
        List<Transation> list=transationService.getAllByUser(userId);
        response.setCode(0);
        response.setMessage("success");
        response.setResult(list);
        return response;
    }

    @GetMapping("/api/transaction/queryall/{name}")
    public Response getAllUser(@PathVariable("name") String name) {
        List<Account> allUsers = new ArrayList<>();
        byte[] bytes = hyperledgerService.getAllUser();
        String toBeProcessed = new String(bytes);
        JSONObject jsonObject = new JSONObject(toBeProcessed);
        JSONArray jsonArray = jsonObject.getJSONArray("accounts").getJSONArray(0);
        System.out.println(jsonArray.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject accountJsonObject = jsonArray.getJSONObject(i);
            String accountId = accountJsonObject.getString("accountId");
            if (accountId.equals(name)) continue;
            Double elecAmount = accountJsonObject.getDouble("elecAmount");
            Double balance = accountJsonObject.getDouble("balance");
            Account account = new Account(accountId, elecAmount, balance, "Not Showing!");
            allUsers.add(account);
        }
        return ResponseFactory.buildSuccessResult(allUsers);
    }
}
