package ru.javawebinar.topjava.app.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.javawebinar.topjava.app.AuthorizedUser;
import ru.javawebinar.topjava.user.model.Role;
import ru.javawebinar.topjava.user.model.User;
import ru.javawebinar.topjava.user.repository.UserRepository;
import ru.javawebinar.topjava.user.repository.datajpa.CrudUserRepository;
import ru.javawebinar.topjava.user.repository.datajpa.DataJpaUserRepository;
import ru.javawebinar.topjava.user.service.UserService;


import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig {
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final DataJpaUserRepository userRepository;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    /*
    @Bean
    UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            User optionalUser = userRepository.getByEmail(email);
            return new AuthorizedUser(optionalUser);
        };
    }

     */



    //https://stackoverflow.com/a/76538979/548473
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**").authorizeHttpRequests(authz ->
                        authz.requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/profile").anonymous()
                                .requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                                .requestMatchers("/api/**").authenticated())
                .httpBasic(withDefaults())
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}