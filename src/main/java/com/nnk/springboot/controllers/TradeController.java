package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Trade;

import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class TradeController {


    private final TradeRepository tradeRepository;
    private static final Logger logger = LogManager.getLogger(TradeController.class);

    public TradeController(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        List<Trade> tradeList = tradeRepository.findAll();
        model.addAttribute("trades", tradeList);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {

        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeRepository.save(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{tradeId}")
    public String showUpdateForm(@PathVariable("tradeId") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade : " + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{tradeId}")
    public String updateTrade(@PathVariable("tradeId") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeRepository.save(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
       tradeRepository.deleteById(id);
        return "redirect:/trade/list";
    }
}
