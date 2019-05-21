package com.friday.colini.firdaycoliniaccountapi.service;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.AuthProviders;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import com.friday.colini.firdaycoliniaccountapi.security.UserPrincipal;
import com.friday.colini.firdaycoliniaccountapi.security.oauth.user.OAuth2UserInfo;
import com.friday.colini.firdaycoliniaccountapi.security.oauth.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        Optional<Account> accountOptional = accountRepository.findByEmail(oAuth2UserInfo.getEmail());

        Account account;
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
        } else {
            Account accountDto = new Account(
                    oAuth2UserInfo.getName(),
                    oAuth2UserInfo.getEmail(),
                    oAuth2UserInfo.getImageUrl(),
                    oAuth2UserInfo.isEmailVerified(),
                    oAuth2UserInfo.getId(),
                    AuthProviders.valueOf(registrationId)
            );
            account = accountRepository.save(accountDto);
        }
        return UserPrincipal.create(account, oAuth2UserInfo.getAttributes());
    }
}