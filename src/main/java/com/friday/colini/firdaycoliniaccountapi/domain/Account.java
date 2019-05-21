package com.friday.colini.firdaycoliniaccountapi.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String userName;
    private String imageUrl;
    @Enumerated(value = EnumType.STRING)
    private RoleType role;
    private boolean mailYn;
    private boolean status;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<AuthProviders> providers;
    private String providerId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public Account(String email,
                   String password,
                   String userName,
                   RoleType role,
                   boolean mailYn,
                   boolean status,
                   Set<AuthProviders> providers,
                   LocalDateTime createAt,
                   LocalDateTime updateAt) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.mailYn = mailYn;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.providers = new HashSet<>(Arrays.asList(AuthProviders.local));
    }

    public Account(
            String userName,
            String email,
            String imageUrl,
            boolean mailYn,
            String providerId,
            AuthProviders providers
    ) {
        this.userName = userName;
        this.email = email;
        this.password = "Auth2 SignUp";
        this.imageUrl = imageUrl;
        this.role = RoleType.USER;
        this.mailYn = mailYn;
        this.status = true;
        this.providerId = providerId;
        this.providers = new HashSet<>(Arrays.asList(providers
        ));
    }
}
