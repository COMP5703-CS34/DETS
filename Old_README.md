# Project Title
Vue + Springboot(Java)

## Table of Contents

- [About](#about)
- [Getting Started]
- [Usage](#usage)
- [Contributing](../CONTRIBUTING.md)

## About <a name = "about"></a>

A simple unfinished framework of Vue + axois + Springboot (Java).
Testing environment:
    System: Ubuntu20.04 on Oracle Virtualbox 6.1
    Tool: VSCode

## Getting Started

### Prerequisites
Java: 11  
Python: 2  
node.js v15.14.0  
npm 7.7.6
```
curl -sL https://deb.nodesource.com/setup_15.x | sudo -E bash –
sudo apt-get install -y nodejs
node -v && npm -v
```
Vue-cli
```
npm install -g @vue/cli
```
Maven
```
sudo apt-get install Maven
```

### Installing

1. Start frontend
New terminal
```
cd vue
```
First time install or after big change
```
npm install
```
Run frontend
```
npm run serve
```
If successful, open explore and enter the address on the terminal,you will see the homepage.

End frontend with frontend with Crtl + Z.
Before hand to github or after large change
```
rm -rf node_modules/
```
Reinstall nodes
```
npm install
```
2. Start backend
New terminal
```
cd backend
./mvnw spring-boot:run
```
If successful you will see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.3)
 ```
 And in the few lines from the bottom
 ```
 INFO xxxx --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 3000 (http) with context path ''
 ```
 Open a explore and enter:
 ```
 https://localhost:3000
 ```


## Usage <a name = "usage"></a>

You can see test data of backend on: localhost:3000/api/test
