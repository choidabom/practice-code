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
