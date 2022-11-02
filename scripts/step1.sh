#! #!/bin/bash

cd ../chaincode
sudo ./minifab cleanup
wait
git restore *
wait
cd ../scripts
sh initMysql.sh
wait
cd ../chaincode
sudo ./minifab up -o stateA.elec.com -i 2.1 -n elec-chaincode -l java -v 1.0 -d true -e true -p '"init"'
