package com.friday.colini.firdaycoliniaccountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties("my-app")
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
    @NotEmpty
    private String adminId;
    @NotEmpty
    private String adminPassword;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;


}
