#!/bin/bash

cd ../vue; 
if [ ! -d "node_modules" ]; then
    npm install
fi
wait; 
npm run serve
