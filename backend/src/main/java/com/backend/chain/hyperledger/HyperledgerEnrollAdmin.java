package com.backend.chain.hyperledger;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class HyperledgerEnrollAdmin {
    private static final Path WALLET_PATH = Paths.get("src", "main", "resources", "wallets");
    private static final String IP_PATH = "src/main/resources/IPAddress.txt";

    public static void enrollAdmin() throws Exception {
        Properties props = new Properties();
        props.put("pemFile",
                "../chaincode/vars/keyfiles/peerOrganizations/stateA.elec.com/ca/ca1.stateA.elec.com-cert.pem");
        props.put("allowAllHostNames", "true");
        String IP_ADDRESS = null;
        try (Scanner sc = new Scanner(new FileReader(IP_PATH))) {
            String line = sc.nextLine();
            IP_ADDRESS = line;
            System.out.println(IP_ADDRESS);
        }
        HFCAClient caClient = HFCAClient.createNewInstance(String.format("https://%s:7000", IP_ADDRESS), props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(WALLET_PATH);

        // Check to see if we've already enrolled the admin user.
        if (wallet.get("admin") != null) {
            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
        Identity user = Identities.newX509Identity("stateA-elec-com", enrollment);
        wallet.put("admin", user);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }
}
