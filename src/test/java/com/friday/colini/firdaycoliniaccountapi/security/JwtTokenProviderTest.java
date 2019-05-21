package com.friday.colini.firdaycoliniaccountapi.security;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    private final String USER_NAME = "user@email.com";
    private final String PASSWORD = "user";
    private final String JWt_SECRET_KEY = "HS123";
    private String jwt_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNTU3OTc2MTIwLCJleHAiOjE1NTc5NzYxMjB9.NZLDeWA5qR1beZS-yA00qrujc3PkSdzPaedS8FLjgqQJWCZ2hGb85FHB1TKRv8Dc388mceWHvQIlHrPvv52ypw";

    @Before
    public void token_generator() throws Exception {
        UserPrincipal userPrincipal = getUserPrincipal(USER_NAME, PASSWORD);
        jwt_token = jwtTokenProvider.generateToken(userPrincipal);
    }

    private UserPrincipal getUserPrincipal(String userName,
                                           String password) {
        Account account = getAccount(userName, password);
        return UserPrincipal.create(account);
    }

    private Account getAccount(String email,
                               String password) {
        Account account = Account.builder()
                .userName("user")
                .email(email)
                .password(password)
                .role(RoleType.USER)
                .build();
        account.setId((long) 2);
        return account;
    }

    @Test
    public void token_parse_user_id() {
        int expected = 2;

        Claims claims = Jwts.parser()
                .setSigningKey(JWt_SECRET_KEY)
                .parseClaimsJws(jwt_token)
                .getBody();

        long userId = Long.parseLong(claims.getSubject());

        assertThat(userId).isEqualTo(expected);
    }

    @Test
    public void account_converted_UserPrincipal() {
        Account account = getAccount(USER_NAME, PASSWORD);

        UserPrincipal userPrincipal = UserPrincipal.create(account);

        assertThat(userPrincipal.getId()).isEqualTo(account.getId());
        assertThat(userPrincipal.getUserName()).isEqualTo(account.getUserName());
        assertThat(userPrincipal.getEmail()).isEqualTo(account.getEmail());
        assertThat(userPrincipal.getPassword()).isEqualTo(account.getPassword());
    }


}