package com.friday.colini.firdaycoliniaccountapi.dto;


import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AccountDto {

    @Getter
    public static class Res {
        private Long id;
        private String email;
        private String password;
        private String userName;
        private Set<RoleType> roles;
        private boolean mailYn;
        private boolean status;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

        @Builder
        public Res(Long id,
                   String email,
                   String password,
                   String userName,
                   Set<RoleType> roles,
                   boolean mailYn,
                   boolean status,
                   LocalDateTime createAt,
                   LocalDateTime updateAt) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.userName = userName;
            this.roles = roles;
            this.mailYn = mailYn;
            this.status = status;
            this.createAt = createAt;
            this.updateAt = updateAt;
        }

        public static Res of(Account account){
             return Res.builder()
                     .id(account.getId())
                     .email(account.getEmail())
                     .password(account.getPassword())
                     .userName(account.getUserName())
                     .mailYn(account.isMailYn())
                     .status(account.isStatus())
                     .roles(new HashSet<>(Arrays.asList(account.getRole())))
                     .createAt(account.getCreateAt())
                     .updateAt(account.getCreateAt())
                     .build();
        }
    }
}
