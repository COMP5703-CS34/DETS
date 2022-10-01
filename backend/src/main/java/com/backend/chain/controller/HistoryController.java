package com.backend.chain.controller;

import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
import com.backend.chain.service.HyperledgerService;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * History Controller
 *
 * @author Mia Zhao
 * @date 2022/10
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HistoryController {

    @Autowired
    HyperledgerService hyperledgerService;

    @GetMapping("/api/History/{name}")
    public Response getHistory(@PathVariable("name") String name) {

        List<List<String>> allHistory = new ArrayList<>();

        byte[] bytes = hyperledgerService.getHisotry(name);
        String Process = new String(bytes);
        JSONObject jsonObject = new JSONObject(Process);
        JSONArray jsonArray = jsonObject.getJSONArray("transactions").getJSONArray(0);

        System.out.println(jsonArray.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            List<String> history = new ArrayList<>();
            JSONObject historyJsonObject = jsonArray.getJSONObject(i);
            String transactionID = historyJsonObject.getString("transactionID");
            String timestamp = historyJsonObject.getString("timestamp");
            String accountId = historyJsonObject.getString("accountId");
            double elecAmount = historyJsonObject.getDouble("elecAmount");
            double balance = historyJsonObject.getDouble("balance");
            Boolean isDeleted = historyJsonObject.getBoolean("isDeleted");

            String elecAmountString = String.valueOf(elecAmount);
            String balanceString = String.valueOf(balance);
            String isDeletedString = isDeleted.toString();

            history.add(transactionID);
            history.add(timestamp);
            history.add(accountId);
            history.add(elecAmountString);
            history.add(balanceString);
            history.add(isDeletedString);

            allHistory.add(history);
        }

        return ResponseFactory.buildSuccessResult(allHistory);
    }
}
