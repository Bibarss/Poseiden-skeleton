package com.nnk.springboot.controller;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@Transactional
public class RuleNameControllerTests {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private MockMvc mockMvc;

    private RuleName testRuleName;

    /**
     * Initialise MockMvc avant chaque test.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Initialisation d'un objet RuleName pour les tests
        testRuleName = new RuleName();

        testRuleName.setName("Rule Name");
        testRuleName.setDescription("Description");
        testRuleName.setJson("Json");
        testRuleName.setTemplate("Template");
        testRuleName.setSqlStr("SQL");
        testRuleName.setSqlPart("SQL Part");
    }

    /**
     * Teste l'affichage de la page de transfert d'argent.
     */
    @Test
    @WithMockUser(username = "user4@example.com")
    public void testShowRuleNameListPage() throws Exception {

        // Enregistrer un RuleName pour le test
        ruleNameRepository.save(testRuleName);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));
    }

    @Test
    @WithMockUser
    public void testAddRuleNameForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        // Effectuer une requête POST sur /ruleName/validate avec des paramètres valides
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule Name2")
                        .param("description", "description2")
                        .param("json", "json2")
                        .param("template", "template2")
                        .param("sqlStr", "SQL2")
                        .param("sqlPart", "SQLPart2")
                        .with(csrf())) // Si la protection CSRF est activée
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        // Vérifier que le RuleName a été ajouté en base de données
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        assertThat(ruleNames, hasItem(hasProperty("name", is("Rule Name2"))));
    }



    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        // Enregistrer un RuleName pour le test
        RuleName savedRuleName = ruleNameRepository.save(testRuleName);

        // Effectuer une requête GET sur /ruleName/update/{id}
        mockMvc.perform(get("/ruleName/update/" + savedRuleName.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().attribute("ruleName", hasProperty("id", is(savedRuleName.getId()))));
    }

    @Test
    @WithMockUser
    public void testUpdateRuleName() throws Exception {
        // Enregistrer un RuleName pour le test
        RuleName savedRuleName = ruleNameRepository.save(testRuleName);

        // Effectuer une requête POST sur /ruleName/update/{id} avec des données mises à jour
        mockMvc.perform(post("/ruleName/update/" + savedRuleName.getId())
                        .param("name", "Rule Name22")
                        .param("description", "description2")
                        .param("json", "json2")
                        .param("template", "template2")
                        .param("sqlStr", "SQL2")
                        .param("sqlPart", "SQLPart2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        // Vérifier que le RuleName a été mis à jour
        RuleName updatedRuleName = ruleNameRepository.findById(savedRuleName.getId()).orElse(null);
        assertNotNull(updatedRuleName);
        assertThat(updatedRuleName.getName(), is("Rule Name22"));
    }

    @Test
    @WithMockUser
    public void testDeleteRuleName() throws Exception {
        // Enregistrer un RuleName pour le test
        RuleName savedRuleName = ruleNameRepository.save(testRuleName);
        int id = savedRuleName.getId();

        // Effectuer une requête GET sur /ruleName/delete/{id}
        mockMvc.perform(get("/ruleName/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        // Vérifier que le RuleName a été supprimé
        assertFalse(ruleNameRepository.existsById(id));
    }

}
