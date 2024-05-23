package com.razermel.diploma.config;


import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/v1/auth/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/v1/invoices/create").hasAuthority("EMPLOYER");
                    req.requestMatchers(HttpMethod.PUT, "/api/v1/invoices/**").hasAnyAuthority("EMPLOYER", "WORKER");
                    req.requestMatchers(HttpMethod.GET, "/api/v1/invoices/**").hasAnyAuthority("EMPLOYER", "WORKER");
                    req.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(); // Включаем настройки CORS

        // Настраиваем CORS
        http.cors(c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Разрешенные домены
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Разрешенные методы
                config.setAllowedHeaders(Arrays.asList("*")); // Разрешаем все заголовки
                return config;
            };
            c.configurationSource(source);
        });

        return http.build();
    }

    // Создаем бин для настройки CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Указываем разрешенные домены
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Указываем разрешенные HTTP методы
        configuration.setAllowedHeaders(Arrays.asList("*")); // Разрешаем все заголовки

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Применяем настройки для определенных URL

        return source;
    }
}
