# I will shorten U

| Build Status  | Coverage   |
|---|---|
|![img](https://travis-ci.com/nobe0716/IwsU.svg?branch=develop)|[![codecov](https://codecov.io/gh/nobe0716/IwsU.svg/branch/develop/graph/badge.svg)](https://codecov.io/gh/nobe0716/IwsU) |

## Functional Requirements
1. Web view for url input
2. Shortening key whose length is under 8
3. 1 : 1 = Original : Shortened

## Base
* SpringBoot v1.5.13
  * Web, JPA, Thymeleaf
* DB
  * H2
  
## Installation and Getting Started
* IwsU requires jdk 1.8
* fork repo and clone it

## Building and Run
* To run the application, execute (on Windows):
  ```sh
  gradlew bootRun
  ``` 
  or
  ```sh
  gradlew build && java -jar build\libs\iwsu-0.1.0.jar
  
  ```