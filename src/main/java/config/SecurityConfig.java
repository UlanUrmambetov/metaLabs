package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // отключаем CSRF для тестов REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/api/auth/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // открытые пути
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // убираем стандартную форму логина
                .httpBasic(basic -> basic.disable()); // убираем basic auth

        return http.build();
    }
}
