# How to make the backend work

1. clone the minifab-elec repo, move it into the vueJavaTest and rename it as chaincode (Replace the empty chaincode directory);

2. Init the network with command (don't miss a single parameter or you will fail):

   ```
   sudo ./minifab up -o stateA.elec.com -i 2.1 -n elec-chaincode -l java -v 1.0 -d true -e true -p '"init","Plant","10000","500000","Home","10","100"'
   ```

3. After the network up, check your host address. and replace the HyperledgerEnrollAdmin file's 25th line with your host address.

4. copy the network yaml file to the back resources directory:

   ```
   cp chaincode/vars/profiles/mychannel_connection_for_gosdk.yaml backend/src/main/resources/connection.yaml
   ```