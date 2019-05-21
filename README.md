# Colini 커뮤니티 사이트 : 계정관련 API

## 주요기능
- 회원가입
- 사용자 정보 관리
- 로그인 
- 로그아웃

## 기능 세부설명
1. 회원가입
- 일반 회원가입
[세부조건]
- 비밀번호는 안호화하여 저장한다.
- 식별은 이메일로 한다.

2. 로그인, 로그아웃
- Remember me
- 일반 로그인 : Resource Owner Password Credentials Grant 동작방식
- Requeset username(Email), password 요청하여 인증된 경우, JWT 토큰 발급
![](assets/markdown-img-paste-20190515054624208.png)

- oAuth2 인증방식으로 로그인 인증 후 JWT 발급
![](assets/markdown-img-paste-20190515055537198.png)
- 인증 성공 시 JWT 토큰 발급한다.
- 토큰이 있을 시 : 토큰으로 인증정보 확인한다.
- 토큰 만료 시 처리 : 구상중
- 각 리소스 서버에서 인증 처리는 account-api server로 REMOTE하여 확인한다
    
3. 사용자 정보 관리
- 해당하는 유저 정보
- 비밀번호 변경 : 본인 인증 후 변경 (본인이 아닌경우 401)
    - optional : kakao or email 인증
- 회원탈퇴 : 상태만 변경
    - optional : 탈퇴한지 1년된 회원은 삭제한다. batch 사용
- 프로필 이미지(url) 변경

---

## 개발환경
- IntelliJ
- Spring Boot 2.1.4.RELEASE
- Spring Security 5.1.5.RELEASE
- Spring Security OAuth2 Client
- JWT 3.3.0
- JJWT 0.9

- Gradle 
- Java 8
- Lombok
- JPA
- MySQL 5.6
- H2 (test)

## Package
- controller
    - SessionController 
    - AccountController  
- domain
    - Account
    - Role 
- dto
    - AccountDto
    - SessionDto 
- service
    - SessionService
    - AccountService 
- repository
    - AccountRepository
     
- security : security 설정 테스트 후 통합 
    - config
        - AuthorizationServerConfig
        - ResourceServerConfig
        - SecurityConfig
    - service
        - UserService
    - repository 
        - UserRepository
---

## Rest API
__Service name : account__

| Resources Name | Path | Method | Detail |
|--------|--------|--------|--------|
|session|new|GET| gets the webpage that has the login
|session| |POST| session authenticates credentials against database
|session| |DELETE| destroys seession and redirect to / 
|users| |POST| 계정 생성 (회원가입)
|users|{id}|GET| 해당 {id}계정 정보
|users|{id}|POST| 해당 {id}계정 정보수정
|users|{id}|DELETE| 해당 {id}계정 탈퇴

---
## DataBase modeling
![db_modeling](/docs/assert/markdown-img-paste-20190505003048528.png)
---
