#!/bin/bash

sudo rm backend/src/main/resources/connection.yaml
rm -rf backend/src/main/resources/wallets
cp chaincode/vars/profiles/mychannel_connection_for_gosdk.yaml backend/src/main/resources/connection.yaml
wait
gnome-terminal -t "Frontend: Vue" --window -x bash -c "source vuescript.sh; exec bash"
sleep 1s
gnome-terminal -t "Backend: Springboot" --window -x bash -c "source backscript.sh; exec bash"
