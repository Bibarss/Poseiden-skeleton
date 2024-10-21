package com.nnk.springboot.config;

import com.nnk.springboot.controllers.BidListController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // Récupérer les rôles de l'utilisateur authentifié
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        logger.info("le role est {}", roles.toString());
        // Si l'utilisateur est un admin, redirection vers "/home"
        if (roles.contains("ADMIN")) {
            response.sendRedirect("/home");
        }
        // Sinon, redirection vers "/rating/list"
        else {
            response.sendRedirect("/bidList/list");
        }
    }
}