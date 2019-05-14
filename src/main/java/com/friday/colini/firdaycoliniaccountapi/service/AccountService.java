package com.friday.colini.firdaycoliniaccountapi.service;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.dto.AccountDto;
import com.friday.colini.firdaycoliniaccountapi.exception.AccountNotFoundException;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account signUp(AccountDto.SignUpReq accountReq){
        accountReq.encodePassword(passwordEncoder, accountReq.getPassword());
        Account account = accountReq.toEntity();
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<RoleType> roles) {
        return roles.stream()
                .map(roleType -> new SimpleGrantedAuthority("ROLE_" + roleType.name()))
                .collect(Collectors.toSet());
    }

    public Account search(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return account;
    }
}
