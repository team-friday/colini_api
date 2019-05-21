package com.friday.colini.firdaycoliniaccountapi.service;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.dto.SignUpRequest;
import com.friday.colini.firdaycoliniaccountapi.exception.AccountNotFoundException;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import com.friday.colini.firdaycoliniaccountapi.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(AccountRepository accountRepository,
                                    PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account signUp(SignUpRequest accountReq) {
        accountReq.encodePassword(passwordEncoder, accountReq.getPassword());
        return accountRepository.save(accountReq.toEntity());
    }

    public Account search(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return UserPrincipal.create(account);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        return UserPrincipal.create(account);
    }

    private Collection<? extends GrantedAuthority> authorities(RoleType role) {
        return new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(role.name())));
    }

}
