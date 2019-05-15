package com.friday.colini.firdaycoliniaccountapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sun.net.www.protocol.http.HttpAuthenticator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void has_JWT_Token_Then_Connected_Resource() throws Exception {

        mockMvc.perform(post("/account/users")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        )
                .andDo(print())
                .andExpect(status().isOk());
                ;
    }

    public String bearerToken() throws Exception {
        return "Bearer " + obtainJwt();
    }

    public String obtainJwt() throws Exception {
        return JWT.create()
                .withIssuer("friday")
                .sign(Algorithm.HMAC512("HS123"));
    }
}