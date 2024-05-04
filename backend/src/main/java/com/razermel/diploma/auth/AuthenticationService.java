package com.razermel.diploma.auth;

import com.razermel.diploma.config.JwtService;
import com.razermel.diploma.user.Role;
import com.razermel.diploma.user.User;
import com.razermel.diploma.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .phone_number(request.getPhone_number())
                .full_name(request.getFull_name())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Логируем найденного пользователя
            Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
            logger.info("User found: {}", user);

            // Логируем результат поиска пользователя по username
            logger.info("User found by username: {}", repository.findByUsername(request.getUsername()));

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Логирование ошибки аутентификации
            Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
            logger.error("Authentication failed: {}", e.getMessage());
            throw e; // Повторное выбрасывание исключения для обработки выше
        }
    }
}
