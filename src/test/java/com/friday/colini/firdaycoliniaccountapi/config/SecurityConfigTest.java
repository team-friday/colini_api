package com.friday.colini.firdaycoliniaccountapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.dto.SignInRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    AppProperties appProperties;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @TestDescription("login_page_이동한다")
    public void login_page() {
        given()
                .when()
                .get("/session")
                .then()
                .statusCode(200)
                .contentType("text/html")
                .body(containsString("Login page"));
    }

    @Test
    public void try_google_login() throws Exception {
        given()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/oauth2/authorize/google")
                .then()
                .statusCode(302)
                .header("Location", containsString("https://accounts.google.com/o/oauth2/v2/auth"));
    }

    @Test
    @TestDescription("외부 리소스에서 인증 확인")
    public void when_another_resources_request_token() throws Exception {
        String token = obtainToken(appProperties.getUserId(), appProperties.getUserPassword());
        given()
                .auth()
                .preemptive()
                .oauth2(token)
                .log().all()
                .when()
                .get("http://localhost:8080/account/users/me")
                .then().log().all()
                .body("email", is(appProperties.getUserId()))
        ;
    }

    private String obtainToken(String userName,
                               String Password) throws Exception {
        SignInRequest signInReq = SignInRequest.builder()
                .username(userName)
                .password(Password)
                .build();

        ResultActions perform = mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInReq)));

        String response = perform.andReturn().getResponse().getContentAsString();
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(response).getAsJsonObject();
        return object.get("accessToken").toString().replace("\"", "");
    }
}