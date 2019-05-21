# Spring security OAuth2
 
## Security 기본 정보 (application.yml 참고)
- access token 시간 : 10 min
- access refresh token : 1 hour 
- client id : myApp
- client secret : pass
### Fixture test 계정 (id/password)
- admin : admin@email.com / admin
- user  : user@email.com / user

#### token 발급 
- [POST] /oauth/token
- Authorization input : client id, secret
- Parameter : username : (id), password : (password), grant_type : "password" 
- Response
``` json
{
    "access_token": "77fbbe22-481c-471b-8577-fff150b7ceac",
    "token_type": "bearer",
    "refresh_token": "f92c59ea-50c7-4cc3-8b3e-5cf48dd01662",
    "expires_in": 108,
    "scope": "write read"
}
```
#### 인증 관계없이 접근 가능한 url
- [GET] localhost:8080/api/hello
#### 인증이 필요한 url
- [GET] localhost:8080/account/users/1
- 인증 안했을 시 Response 
```json
{
    "error": "invalid_token",
    "error_description": "Invalid access token: 77fbbe22-481c-471b-8577-fff150b7ceac"
}
```
- 인증 시 Response
```json
{
    "id": 1,
    "email": "admin@email.com",
    "password": "{bcrypt}$2a$10$NhooXkjVwm4.IT7eIUE2s.foc59DviAEZw/6OGgLsJmdnZcM1fSua",
    "userName": "admin",
    "roles": [
        "ADMIN"
    ],
    "mailYn": false,
    "status": true,
    "createAt": null,
    "updateAt": null
}
```