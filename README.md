<!-- PROJECT LOGO -->
<br />

<h3 align="center">Distributed Electricity Transaction System</h3>

  <p align="center">
    a blockchain ledger system to facilitate transactions of electricity
    <br />
<a href="https://github.com/COMP5703-CS34/DETS/blob/master/User%20manual.pdf"><strong>Know more by user manual »</strong></a>
    <br />
    <br />
    <a href="[https://github.com/COMP5703-CS34/DETS](https://drive.google.com/drive/u/1/folders/1qqmfct1e5gjNQ2mqncCDaFH-SQy96QAf)">View Demo</a>
    ·
    <a href="https://github.com/COMP5703-CS34/DETS/issues">Report Bug</a>
    ·
    <a href="https://github.com/COMP5703-CS34/DETS/issues">Request Feature</a>
  </p>




<!-- TABLE OF CONTENTS -->

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

The purpose of this project is to build and develop a web application hosted on a decentralised network to facilitate the transaction of electricity between users. The users can be not only the electricity consumer but also the producer with rooftop solar equipment. Unlike traditional supply systems which can only provide electricity to users, this system allows users to buy or sell excessive electricity among users. This project's scope is to build private online ledgers to process electricity transactions between users using blockchain technology. Due to the distributed feature of the blockchain, the electricity supply system is also distributed which is quite different from the existing system. 

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [Hyperledger](https://github.com/hyperledger)
* Vue + SpringBoot (Java)
* Ubuntu 20.04

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites
#### Backend
This is an example of how to list things you need to use the software and how to install them.
docker
```
sudo apt-get install docker
sudo apt-get install docker-ce
```
Ssh (It is required if the virtual machine needs connection with outside environment)
MySQL (The codes beginning from line 7 should be copied exactly.)
```
sudo mysql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'newpwd'; #newpwd is the new password you set for root
FLUSH PRIVILEGES;
quit;
sudo service mysql restart

mysql -uroot -p #And enter the password you’ve just set
CREATE USER 'detscs341'@'localhost' IDENTIFIED WITH mysql_native_password BY 'detscs341';
GRANT ALL PRIVILEGES ON *.* TO 'detscs341'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
quit;
sudo service mysql restart
```

#### Frontend  
Java 11  
maven  
```
sudo apt-get install Maven
```
node.js v15.14.0  
npm 7.7.6
```
curl -sL https://deb.nodesource.com/setup_15.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install vue-cli
node -v && npm -v
```
Vue-cli
```
sudo npm install -g @vue/cli
```


### Installation
Clone the project into local directory:
```
git clone https://github.com/COMP5703-CS34/DETS.git
```
Go to script dir,
```
cd script
```
Run the script file **step1.sh**
```
sudo sh step1.sh
```
**step1.sh** is to build the database and start the chaincode network. It may take about 10-15 minutes to start up. The successful page should be like:


When the script file **step1.sh** runs successfully, run step2.sh (you can only run **step2.sh** if **step1.sh** runs successfully, otherwise an error will occur). If you get a red prompt or a non-successful screen when running the above command, you need to run the above command again.
```
sudo sh step2.sh
```
**step2.sh** is to start the front and back-end services. A successful run will result in as followed:



Then you can open an explorer and access to the URL shown in the terminal Frontend: Vue. You will see the home page.

**Important:** Do not close these two terminals. If you accidentally close them, you can use
```
sudo sh backscript.sh
# for restarting backend
```
or
```
sudo sh vuescript.sh
# for restarting frontend
```
to start the back-end and front-end respectively.


<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](/user manual.docx)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- CONTACT -->
## Contact

Your Name - [mzha6702@uni.sydney.edu.au](https://twitter.com/twitter_handle) 

Project Link: [https://github.com/COMP5703-CS34/DETS](https://github.com/COMP5703-CS34/DETS)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



