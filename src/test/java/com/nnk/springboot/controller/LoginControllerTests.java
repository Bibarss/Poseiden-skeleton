package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test d'intégration pour le LoginController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    /**
     * Vérifie que l'accès à /login sans paramètre "error" renvoie la vue "login"
     * et n'a pas d'attribut "loginError" dans le Model.
     */
    @Test
    @DisplayName("GET /login sans error -> OK, vue login, pas de message d'erreur")
    void testGetLoginNoErrorParam() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("loginError"));
    }

    /**
     * Vérifie que l'accès à /login avec le paramètre "error=true" ajoute
     * "loginError" dans le Model et renvoie la vue "login".
     */
    @Test
    @DisplayName("GET /login?error=true -> OK, vue login, attribut loginError présent")
    void testGetLoginWithErrorParam() throws Exception {
        mockMvc.perform(get("/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginError"))
                .andExpect(model().attribute("loginError", "Votre login ou mot de passe est invalide !"));
    }

    /**
     * Vérifie que l'accès à /secure/article-details renvoie la liste des users
     * (mockée) et la vue "user/list".
     */
    @Test
    @DisplayName("GET /secure/article-details -> OK, vue user/list, users mockés")
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetSecureArticleDetails() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    /**
     * Vérifie que l'accès à /error/403 renvoie un message et la vue "error/403".
     */
    @Test
    @DisplayName("GET /error/403 -> OK, vue error/403, attribut errorMsg présent")
    void testGetError403() throws Exception {
        mockMvc.perform(get("/error/403"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/403"))
                .andExpect(model().attributeExists("errorMsg"))
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."));
    }
}
