package com.backend.chain.controller;

import com.backend.chain.entity.History;
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
        List<History> allHistory = new ArrayList<>();
        byte[] bytes = hyperledgerService.getHisotry(name);
        String Process = new String(bytes);
        JSONObject jsonObject = new JSONObject(Process);
        JSONArray jsonArray = jsonObject.getJSONArray("transactions").getJSONArray(0);
        System.out.println(jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject historyJsonObject = jsonArray.getJSONObject(i);
            String transactionID = historyJsonObject.getString("transactionId");
            String timestamp = historyJsonObject.getString("timestamp");
            String accountId = historyJsonObject.getString("accountId");
            double elecAmount = historyJsonObject.getDouble("elecAmount");
            double balance = historyJsonObject.getDouble("balance");
            Boolean isDeleted = historyJsonObject.getBoolean("isDeleted");
            History history = new History(transactionID, timestamp, accountId, elecAmount, balance, isDeleted);
            allHistory.add(history);
        }
        return ResponseFactory.buildSuccessResult(allHistory);
    }
}
