package com.friday.colini.firdaycoliniaccountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.config.AppProperties;
import com.friday.colini.firdaycoliniaccountapi.dto.SessionDto;
import com.friday.colini.firdaycoliniaccountapi.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AppProperties appProperties;

    private String USER_NAME;
    private String USER_PASSWORD;

    @Before
    public void setUp() throws Exception {
        USER_NAME = appProperties.getUserId();
        USER_PASSWORD = appProperties.getUserPassword();
    }

    @Test
    @TestDescription("로그인 성공 할 시 JWT 토큰 발급한다")
    public void login_Success() throws Exception {
        SessionDto.SignInReq signInReq = SessionDto.SignInReq.builder()
                .userName(USER_NAME)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post("/session/sign-in")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInReq))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("tokenType").value("Bearer"))
        ;
    }

    @Test
    public void UsernamePasswordAuthenticationToken_Create() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(USER_NAME, USER_PASSWORD);
        Authentication authentication =authenticationManager.authenticate(token);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        assertThat(token).isNotNull();
        assertThat(userPrincipal).isInstanceOf(UserPrincipal.class);
    }

}