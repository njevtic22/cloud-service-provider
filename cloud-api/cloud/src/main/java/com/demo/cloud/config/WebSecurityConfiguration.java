package com.demo.cloud.config;

import com.demo.cloud.security.AppAuthenticationEntryPoint;
import com.demo.cloud.security.TokenAuthenticationFilter;
import com.demo.cloud.security.TokenUtil;
import com.demo.cloud.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {
    private final TokenUtil tokenUtil;
    private final UserDetailsServiceImpl service;
    private final AppAuthenticationEntryPoint entryPoint;

    public WebSecurityConfiguration(TokenUtil tokenUtil, UserDetailsServiceImpl service, AppAuthenticationEntryPoint entryPoint) {
        this.tokenUtil = tokenUtil;
        this.service = service;
        this.entryPoint = entryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(encoder);
        provider.setUserDetailsService(service);
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/", "/webjars/**", "/*.html", "/favicon.ico", "/*/*.html",
                                "/*/*.css", "/*/*.js").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtil, service), BasicAuthenticationFilter.class);

        // TODO:
        http.cors(cors -> cors.configure(http));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
