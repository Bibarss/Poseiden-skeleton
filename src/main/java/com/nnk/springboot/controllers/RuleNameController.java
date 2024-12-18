package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;
    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {

        logger.info("Affichage du tableau des ruleName.");
        List<RuleName> ruleNameList = ruleNameService.findAll();
        model.addAttribute("ruleNames", ruleNameList);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {

        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";

    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName = ruleNameService.findById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        ruleNameService.delete(ruleNameService.findById(id));
        List<RuleName> ruleNameList = ruleNameService.findAll();
        model.addAttribute("ruleNames", ruleNameList);

        return "redirect:/ruleName/list";
    }
}
