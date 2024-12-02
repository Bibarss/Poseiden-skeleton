package com.nnk.springboot.config;



import com.nnk.springboot.service.UserService;
import com.nnk.springboot.service.impl.CustomUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomAuthenticationSuccessHandler successHandler, CustomUserDetailsService customUserDetailsService) {
        this.successHandler = successHandler;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login").permitAll()  // Autoriser l'accès public à ces routes
                        .requestMatchers("/user/**").hasAuthority("ADMIN") // Admin seulement
                        .anyRequest().authenticated()  // Toutes les autres pages nécessitent une authentification
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(successHandler)  // Utilisation du CustomAuthenticationSuccessHandler
                        .permitAll()
                )
                .logout(logout -> {
                    logger.info("Configuration de la déconnexion");
                    logout
                            .logoutSuccessUrl("/login?logout")
                            .invalidateHttpSession(true) // Invalide la session
                            .deleteCookies("JSESSIONID") // Supprime les cookies de session
                            .permitAll();
                })
                .userDetailsService(customUserDetailsService); // Utiliser le userService injecté


        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}