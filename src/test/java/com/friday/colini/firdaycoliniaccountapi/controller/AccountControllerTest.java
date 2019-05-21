package com.friday.colini.firdaycoliniaccountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.config.AppProperties;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.dto.SignInRequest;
import com.friday.colini.firdaycoliniaccountapi.dto.SignUpRequest;
import com.friday.colini.firdaycoliniaccountapi.service.CustomUserDetailsService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
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
    @TestDescription("id로 계정 조회")
    public void findById_Success() throws Exception {
        long id = 1;

        signUp_success();

        mockMvc.perform(get("/account/users/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value("juyoung"));
    }

    @Test
    @TestDescription("입력값이 들어오지 않는 경우 에러 발생한다")
    public void signUp_Bad_Request_Empty_Input() throws Exception {
        SignUpRequest account = SignUpRequest.builder().build();

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                ;
    }

    @Test
    @TestDescription("입력값이 잘못 들어온 경우 에러 발생한다")
    public void signUp_Bad_Request_Wrong_Input() throws Exception {
        SignUpRequest account = SignUpRequest.builder()
                .userName("")
                .email("juyoung@email.com")
                .password("password")
                .mailYn(false)
                .role(RoleType.USER)
                .build();

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("계정등록 성공")
    public void signUp_success() throws Exception {
        String userName = "juyoung";
        SignUpRequest account = SignUpRequest.builder()
                .userName(userName)
                .email("juyoung@email.com")
                .password("password")
                .mailYn(false)
                .role(RoleType.USER)
                .build();

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(Matchers.not(0)))
                .andExpect(jsonPath("userName").value(userName))
        ;
    }

    @Test
    @TestDescription("인증된 토큰은 리소스 접속에 성공한다")
    public void Security_Interceptor() throws Exception {
        String bearerToken = obtainToken();
        mockMvc.perform(get("/account/users/1")
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @TestDescription("토큰 정보 확인")
    public void information_for_token() throws Exception {
        String bearerToken = obtainToken();
        System.out.println(bearerToken);
        mockMvc.perform(get("/account/users/me")
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    public String obtainToken() throws Exception {
        SignInRequest signInReq = SignInRequest.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .build();

        ResultActions perform = mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInReq)));

        String response = perform.andReturn().getResponse().getContentAsString();
//        JsonObject object = jsonParser.parse(response).getAsJsonObject();
//        return "Bearer " + object.get("accessToken").toString().replace("\"", "");
        return "";
    }
}