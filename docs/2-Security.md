# Spring security OAuth2
 
## Spring Security5 + OAuth2 + JWT 연동
- 인증 성공 시 Response data를 Cookie에 임시 저장한다. 
(기존에는 인증 시 jssseionid에 인증 정보가 저장된다. 현 서버는 stateless로 통신이 불가하기 때문에, 
response 결과값을 Cookie에 임시 저장 후 인증서버(ex) google)에서 인증 후 쿠키값을 삭제한다.
) 
- 인증 실패, 성공 시 쿠키 저장 값 삭제 
- 인증 성공 
    - 계정 확인 후 없으면 계정 생성한다. ( default : ROLE_USER )
    - 기존에 계정이 존재하는 경우, 패스워드를 제외한 데이터 변경 및 provider 추가

- 토큰이 있을 시 : 토큰으로 인증정보 확인한다.
- 토큰 만료 시 처리 : 구상중

## Todo
-[ ] Create JWT response model
-[ ] 토큰 부여 방법 
-[ ] 리팩토링 
-[ ] Role relation change 1:1
-[ ] Accouunt insert `Provider`(1:N), `ProviderId`
-[ ] Exception 처리
-[ ] build.gradle compile > implement 

### 참고
[Spring Security 5 OAuth 2.0 Login and Sign Up in Stateless REST Web Services](https://dzone.com/articles/spring-security-5-oauth-20-login-and-signup-in-sta)
[callicoder](https://github.com/callicoder/spring-boot-react-oauth2-social-login-demo/blob/master/spring-social/src/main/java/com/example/springsocial/config/AppProperties.java)


