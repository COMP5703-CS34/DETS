package com.backend.chain.service;

import com.backend.chain.hyperledger.HyperledgerConnection;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

/**
 * Hyperledger Handler
 *
 * @author Yiwei Xu
 * @date 2022/10
 */

@Service
public class HyperledgerService {
    private static final Contract contract = HyperledgerConnection.getInstance();
    private static final String NO_OBJECT_WARNING = "No Such object!";

    public byte[] getUserInfo(String name) {
        byte[] bytes = NO_OBJECT_WARNING.getBytes();
        if (name == null) return bytes;
        try {
            bytes = contract.evaluateTransaction("query", name);
            //输出测试结果

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] getAllUser() {
        byte[] bytes = null;
        try {
            bytes = contract.evaluateTransaction("queryAllAccount");
            // System.out.print(new String(bytes));
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public byte[] makeTransfer(String fromAccount, String toAccount, String elecAmount, String elecPrice) {
        byte[] bytes = null;

        try {
            bytes = contract.submitTransaction("transfer", toAccount, fromAccount, elecAmount, elecPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] login(String name){
        byte[] bytes = NO_OBJECT_WARNING.getBytes();
        if (name == null) return bytes;
        try {
            bytes = contract.evaluateTransaction("getPassword", name);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] addUser(String name, String elecAmount, String balance, String password, String identity,
                          String adminID) {
        byte[] bytes = null;

        try {
            bytes = contract.submitTransaction("add", name, elecAmount, balance, password, identity, adminID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] deleteUser(String name) {
        byte[] bytes = NO_OBJECT_WARNING.getBytes();
        if (name == null) return bytes;
        try {
            bytes = contract.submitTransaction("delete", name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] updateUser(String name, String elecAmount, String balance, String identity) {
        byte[] bytes = NO_OBJECT_WARNING.getBytes();

        try {
            bytes = contract.submitTransaction("update", name, elecAmount, balance, identity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] setPassword(String name,String password) {
        byte[] bytes = NO_OBJECT_WARNING.getBytes();

        try {
            bytes = contract.submitTransaction("setPassword", name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public byte[] getHistory(String name) {
        byte[] bytes = null;

        try {
            bytes = contract.evaluateTransaction("queryHistory", name);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
