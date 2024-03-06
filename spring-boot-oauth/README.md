# Spring Boot OAuth Project

### Spring Boot OAuth 개발환경

- Project : Gradle - Groovy
- Language : Java
- Spring Boot : 3.1.5
- Packaging : Jar
- Java : 17(JDK 버전)
- Dependencies : Spring Web, Lombok, Spring Data JPA, H2 Database

### 삽질

**문제와 해결 1**

- user 관련 테이블을 만들 때 **테이블명을 user로 짓는 경우** expected "identifier" 오류가 발생한다.
- 바로 user는 H2 database의 keyword이기 때문에 **user가 아닌 다른 이름으로 작명해야한다.** -> "users"

**문제와 해결 2**

- MySQL 데이터베이스의 JDBC 드라이버 클래스(com.mysql.cj.jdbc.Driver)를 찾지 못해서 발생
- 의존성 추가 : `implementation mysql:mysql-connector-java:8.0.23'` 해결 ...!

---

## OAuth란?
**인가**를 위한 기술 (제각각인 인증 방식을 표준화하기 위해 만들어진 보안 프로토콜)

## OAuth의 핵심 포인트 
### **인증**은 유저가 직접 / **권한**은 서비스에게

- 인증(Authentication): 사용자가 어플리케이션에 대해서 인증된 사용자인지 확인하는 절차
- 권한 부여(Authorization): 특정 리소스나 서비스에 접근할 수 있는 권한을 허용하는 것

## OAuth 2.0에서 등장하는 4가지 주요 개념
 
### 1. Client (Resource를 사용할 웹 어플리케이션)
- Client는 Resource Server에 접속해서 정보를 가져오고자 하는 클라이언트 (웹 어플리케이션)이다.

### 2. Resource Owner (자원 소유자)
- Client가 제공하는 서비스를 통해 로그인하는 실제 유저가 이에 속한다.
  - 예를 들자면, 다른 서비스에서 네이버 아이디를 이용하여 로그인하려는 사용자이다.

### 3. Authorization Server (OAuth 인증 서버)
- Client가 Resource Server의 서비스를 사용할 때 사용하는 Access Token을 발행한다.

### 4. Resourec Server (REST API Server)
- Client가 제어하고자 하는 자원을 보유하고 있는 서버이다.
  - Facebook, Google, Naver 등이 이에 속한다.

