<!-- PROJECT LOGO -->
<br />

<h3 align="center">Distributed Electricity Transaction System</h3>

  <p align="center">
    a blockchain ledger system to facilitate transactions of electricity
    <br />
    <a href="https://github.com/COMP5703-CS34/DETS"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/COMP5703-CS34/DETS">View Demo</a>
    ·
    <a href="https://github.com/COMP5703-CS34/DETS/issues">Report Bug</a>
    ·
    <a href="https://github.com/COMP5703-CS34/DETS/issues">Request Feature</a>
  </p>

</div>



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
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

The purpose of this project is to build and develop a web application hosted on a decentralised network to facilitate the transaction of electricity between users. The users can be not only the electricity consumer but also the producer with rooftop solar equipment. Unlike traditional supply systems which can only provide electricity to users, this system allows users to buy or sell excessive electricity among users. This project's scope is to build private online ledgers to process electricity transactions between users using blockchain technology. Due to the distributed feature of the blockchain, the electricity supply system is also distributed which is quite different from the existing system. 

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [Hyperledger](https://github.com/hyperledger)
* Vue + SpringBoot (Java)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

#### Backend

This is an example of how to list things you need to use the software and how to install them.

- docker
- maven
- ssh

#### Frontend

- Java 11

- node.js v15.14.0

- npm 7.7.6

  ```
  curl -sL https://deb.nodesource.com/setup_15.x | sudo -E bash –
  sudo apt-get install -y nodejs
  node -v && npm -v
  ```

- Vue-cli

  ```
  npm install -g @vue/cli
  ```

- Maven

  ```
  sudo apt-get install Maven
  ```

### Installation

#### First of all

1. Clone the project into local directory:

   ```
   git clone https://github.com/COMP5703-CS34/minifab-elec.git
   ```

#### Chaincode

1. Go to [minifab-elec](https://github.com/COMP5703-CS34/minifab-elec), pull the chaincode of this project into local directory

2. Move minifab-elec into DETS, and rename it as **chaincode**:

   ```
   rm -rf DETS/chaincode
   cp -r minifab-elec DETS/chaincode
   ```

3. Go to chaincode dir, initialize the chain:

   ```
   sudo ./minifab up -o stateA.elec.com -i 2.1 -n elec-chaincode -l java -v 1.0 -d true -e true -p '"init"'
   ```

#### Backend

1. Check resources directory

2. Go to backend dir:

   ```
   cd backend
   ./mvnw spring-boot:run
   ```

3. If successful you will see:

   ```
     .   ____          _            __ _ _
    /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
   ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
    \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
     '  |____| .__|_| |_|_| |_\__, | / / / /
    =========|_|==============|___/=/_/_/_/
    :: Spring Boot ::                (v2.7.3)
   ```

4. And in the few lines from the bottom

   ```
   INFO xxxx --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 3000 (http) with context path ''
   ```

5. Open a explore and enter:

   ```
   https://localhost:3000
   ```

#### Frontend

1. Come to frontend directory:

   ```
   cd vue
   ```

2. First time install or after big change:

   ```
   npm install
   ```

3. Run frontend. 

   ```
   npm run serve
   ```

4. If successful, open random explorer and enter address shown on terminal, you will see the homepage. End frontend with `Crtl + Z`

5. Before hand to github or after large change:

   ```
   rm -rf node_modules/
   npm install
   ```

6. 

7. 

   

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Feature 1
- [ ] Feature 2
- [ ] Feature 3
    - [ ] Nested Feature

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues).

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



<!-- LICENSE -->

## License

~~Distributed under the MIT License. See `LICENSE.txt` for more information.~~

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@twitter_handle](https://twitter.com/twitter_handle) - email@email_client.com

Project Link: [https://github.com/github_username/repo_name](https://github.com/github_username/repo_name)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* []()
* []()
* []()

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

