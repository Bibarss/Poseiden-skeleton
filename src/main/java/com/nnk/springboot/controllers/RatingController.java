package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class RatingController {


    private final RatingRepository ratingRepository;

    private static final Logger logger = LogManager.getLogger(RatingRepository.class);

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    // Fait
    @RequestMapping("/rating/list")
    public String home(Model model) {

        List<Rating> ratingList = ratingRepository.findAll();
        model.addAttribute("ratings", ratingList);
        return "rating/list";
    }

    // Fait
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {

        model.addAttribute("rating", new Rating());

        return "rating/add";
    }

    //Fait
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingRepository.save(rating);
        return "redirect:/rating/list";
    }

    //Fait
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating id: " + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingRepository.save(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingRepository.deleteById(id);
        return "redirect:/rating/list";
    }
}
