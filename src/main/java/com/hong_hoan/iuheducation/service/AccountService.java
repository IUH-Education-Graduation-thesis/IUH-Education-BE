package com.hong_hoan.iuheducation.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hong_hoan.iuheducation.entity.Account;
import com.hong_hoan.iuheducation.entity.SinhVien;
import com.hong_hoan.iuheducation.exception.BadTokenException;
import com.hong_hoan.iuheducation.exception.UserAlreadyExistsException;
import com.hong_hoan.iuheducation.repository.AccountRepository;
import com.hong_hoan.iuheducation.resolvers.input.CreateAccountInput;
import com.hong_hoan.iuheducation.security.JWTUserDetails;
import com.hong_hoan.iuheducation.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.hong_hoan.iuheducation.util.StreamUtils.collectionStream;
import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private static final String ADMIN_AUTHORITY = "ADMIN";
    private static final String STUDENT_AUTHORITY = "STUDENT";
    private static final String TEACHER_AUTHORITY = "TEACHER";
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties properties;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    @Transactional
    public JWTUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUserName(username).map(account -> getUserDetails(account, getToken(account))).orElseThrow(() -> new UsernameNotFoundException("T??i kho???n ho???c m???t kh???u kh??ng ????ng!"));
    }

    @Transactional
    public JWTUserDetails loadAccountByToken(String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(accountRepository::findByUserName)
                .map(account -> getUserDetails(account, token))
                .orElseThrow(BadTokenException::new);
    }

    @Transactional
    public Account getCurrentAccount() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(accountRepository::findByUserName)
                .orElse(null);
    }

    @Transactional
    public Account createAccount(SinhVien sinhVien, CreateAccountInput input) {
        if (!exists(input.getUsername())) {
            Set<String> _listRole = new HashSet<String>();

            input.getRoles().forEach(i -> {
                _listRole.add(i.name());
            });

            return accountRepository.saveAndFlush(Account
                    .builder()
                    .userName(input.getUsername())
                    .password(passwordEncoder.encode(input.getPassword()))
                    .roles(_listRole)
                    .sinhVien(sinhVien)
                    .build());
        } else {
            throw new UserAlreadyExistsException(input.getUsername());
        }
    }

    public boolean exists(String username) {
        return accountRepository.existsByUserName(username);
    }
//
//    @Transactional
//    public Account updatePassword(Long userId, UpdatePasswordInput input) {
//        Account account = accountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
//        if (passwordEncoder.matches(input.getCurrentPassword(), account.getPassword())) {
//            account.setPassword(passwordEncoder.encode(input.getNewPassword()));
//        } else {
//            throw new BadCredentialsException(account.getUsername());
//        }
//        return account;
//    }

    public boolean isAdmin() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .stream()
                .flatMap(Collection::stream)
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ADMIN_AUTHORITY::equals);
    }

    public boolean isAuthenticated() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(not(this::isAnonymous))
                .isPresent();
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        if (accountRepository.existsById(userId)) {
            accountRepository.deleteById(userId);
            return true;
        } else {
            return false;
        }
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    @Transactional
    public String getToken(Account account) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(properties.getTokenExpiration());
        return JWT
                .create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(account.getUserName())
                .sign(algorithm);
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private JWTUserDetails getUserDetails(Account account, String token) {
        return JWTUserDetails
                .builder()
                .username(account.getUserName())
                .password(account.getPassword())
                .authorities(collectionStream(account.getRoles())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .token(token)
                .build();
    }

//    public Account findAccountBySinhVienId(int sinhVienId) {
//        List<Object[]> _result = accountRepository.findAccountBySinhVienId(sinhVienId);
//        String _userName = null;
//        if(_result.size() <= 0) {
//            return null;
//        }
//
//        _userName = (String) _result.get(0)[0];
//        Account _account = accountRepository.findByUsername(_userName).get();
//
//        return _account;
//    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    public void deleteAllAccount() {accountRepository.deleteAll();}

}
