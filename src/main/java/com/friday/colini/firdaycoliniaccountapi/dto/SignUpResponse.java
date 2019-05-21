package com.friday.colini.firdaycoliniaccountapi.dto;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {
    private Long id;
    private String email;
    private String password;
    private String userName;
    private RoleType roles;
    private boolean mailYn;
    private boolean status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public SignUpResponse(Long id,
                          String email,
                          String password,
                          String userName,
                          RoleType role,
                          boolean mailYn,
                          boolean status,
                          LocalDateTime createAt,
                          LocalDateTime updateAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.roles = role;
        this.mailYn = mailYn;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static SignUpResponse of(Account account) {
        return SignUpResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .password(account.getPassword())
                .userName(account.getUserName())
                .mailYn(account.isMailYn())
                .status(account.isStatus())
                .role(account.getRole())
                .createAt(account.getCreateAt())
                .updateAt(account.getCreateAt())
                .build();
    }
}