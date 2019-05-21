package com.friday.colini.firdaycoliniaccountapi.dto;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.AuthProviders;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;
    @NotEmpty(message = "password is wrong")
    private String password;
    @NotEmpty(message = "userName is wrong")
    private String userName;
    @NotEmpty(message = "role is wrong")
    private RoleType role;
    private boolean mailYn;
    private String providerId;
    private AuthProviders provider = AuthProviders.local;

    @Builder
    public SignUpRequest(@NotEmpty String email,
                         @NotEmpty String password,
                         @NotEmpty String userName,
                         @NotEmpty RoleType role,
                         boolean mailYn,
                         AuthProviders provider) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.mailYn = mailYn;
        this.provider = provider;
    }

    public Account toEntity() {
        return Account.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .role(role)
                .mailYn(mailYn)
                .status(true)
                .providers(new HashSet<>(Arrays.asList(provider)))
                .build();
    }

    public void encodePassword(PasswordEncoder passwordEncoder,
                               String password) {
        this.password = passwordEncoder.encode(password);
    }
}
