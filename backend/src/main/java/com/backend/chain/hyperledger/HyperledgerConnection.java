package com.backend.chain.hyperledger;

import org.hyperledger.fabric.gateway.*;

import java.nio.file.Path;
import java.nio.file.Paths;


public class HyperledgerConnection {

    private static final Path NETWORK_CONFIG_PATH = Paths.get("backend", "src", "main", "resources", "connection.yaml");
    private static final Path WALLET_PATH = Paths.get("backend", "src", "main", "resources", "wallets");
    private static Gateway gateway = null;
    private static Contract contract = null;

    public static Contract getInstance() {
        if (gateway == null) {
            try {
                HyperledgerEnrollAdmin.enrollAdmin();
                Wallet wallet = Wallets.newFileSystemWallet(WALLET_PATH);

                Gateway.Builder builder = Gateway.createBuilder();
                builder.identity(wallet, "admin").networkConfig(NETWORK_CONFIG_PATH);

                gateway = builder.connect();
                Network network = gateway.getNetwork("mychannel");
                contract = network.getContract("elec-chaincode");

                byte[] bytes = contract.evaluateTransaction("query", "Plant");
                System.out.println("After doing query");
                System.out.println(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return contract;
    }
}
