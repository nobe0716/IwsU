# I will shorten U

| Build Status  | Coverage   |
|---|---|
|![img](https://travis-ci.com/nobe0716/IwsU.svg?branch=develop)|![codecov](https://codecov.io/gh/nobe0716/IwsU/branch/develop/graph/badge.svg)|

## Functional Requirements
1. Web view for url input
2. Shortening key whose length is exactly 6 (for convenience of developer :P)
3. 1 : 1 = Original : Shortened

## Strategy
* Simplest implementation
  * Using [String.hashCode](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#hashCode--)
  * decode it as 62-base-decimal (0-9(10) + 26(A-Z) + 26(a-z))
* Avoid hash conflict
  * compose url with 5-len randomAlphaNumeric until finding next valid hashKey

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
  
## Usage
* Visit http://localhost:8080/
* Enter what to be shorten
* Check the link!