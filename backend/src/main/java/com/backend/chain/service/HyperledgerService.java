package com.backend.chain.service;

import com.backend.chain.hyperledger.HyperledgerConnection;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

/**
 * Hyperledger Handler
 *
 * @author Yiwei Xu
 * @date 2022/09
 */

@Service
public class HyperledgerService {
    private static final Contract contract = HyperledgerConnection.getInstance();

    public void getUserInfo(String name) {
        try {
            byte[] bytes = contract.evaluateTransaction("query", name);
            //输出测试结果
            System.out.println(new String(bytes));
        } catch (ContractException e) {
            e.printStackTrace();
        }
    }
}
