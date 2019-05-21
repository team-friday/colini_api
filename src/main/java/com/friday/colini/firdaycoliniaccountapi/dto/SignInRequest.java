package com.friday.colini.firdaycoliniaccountapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    @Builder
    public SignInRequest(@NotEmpty String username,
                         @NotEmpty String password) {
        this.username = username;
        this.password = password;
    }
}
