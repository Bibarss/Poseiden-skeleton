package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Classe de configuration de la sécurité Spring Security.
 * <p>
 * Cette classe configure les règles de sécurité, les filtres d'authentification et d'autorisation,
 * et les encodeurs pour protéger l'application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * Logger pour suivre les événements liés à la configuration de la sécurité.
     */
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    /**
     * Service utilisateur personnalisé pour gérer les authentifications.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructeur pour injecter les dépendances nécessaires.
     *
     * @param customUserDetailsService Service utilisateur personnalisé.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Configure les règles de filtrage et les permissions d'accès pour l'application.
     *
     * @param http L'objet HttpSecurity permettant de configurer la sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     * @throws Exception Si une erreur survient lors de la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/css/**", "/js/**", "/403", "/error/**").permitAll()
                        .requestMatchers("/user/**", "/home", "/").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        // Indiquer quel handler utiliser après une authentification réussie
                        .successHandler(customAuthenticationSuccessHandler())
                        // En cas d’échec (mauvais login / password), on renvoie vers /login?error=true
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // Gère l'accès refusé (403)
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/403"))
                .userDetailsService(customUserDetailsService);

        return http.build();
    }


    /**
     * Configure le fournisseur d'authentification avec le service utilisateur et un encodeur de mots de passe.
     *
     * @return Un fournisseur d'authentification configuré.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        logger.info("Configuration du fournisseur d'authentification avec un encodeur BCrypt.");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Définit un encodeur de mots de passe basé sur BCrypt.
     *
     * @return Un encodeur BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Création de l'encodeur de mots de passe BCrypt.");
        return new BCryptPasswordEncoder();
    }

    /**
     * Définit un gestionnaire personnalisé pour gérer les succès d'authentification.
     *
     * @return Un gestionnaire de succès d'authentification personnalisé.
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        logger.info("Création du gestionnaire personnalisé de succès d'authentification.");
        return new CustomAuthenticationSuccessHandler();
    }
}
