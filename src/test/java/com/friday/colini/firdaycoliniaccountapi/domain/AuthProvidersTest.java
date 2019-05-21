package com.friday.colini.firdaycoliniaccountapi.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthProvidersTest {

    @Test
    public void authProvider_return() {
        String registeredId = "google";

        AuthProviders authProviders = AuthProviders.valueOf(registeredId);

        assertThat(authProviders.name()).isEqualTo(registeredId);
    }
}