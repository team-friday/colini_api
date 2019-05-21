package com.friday.colini.firdaycoliniaccountapi.security.oauth.user;

import com.friday.colini.firdaycoliniaccountapi.domain.AuthProviders;
import org.thymeleaf.util.StringUtils;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   Map<String, Object> attributes) {

        if (StringUtils.equals(registrationId, AuthProviders.google.name())) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        throw new IllegalArgumentException("Login with " + registrationId + " is not supported yet.");
    }
}
