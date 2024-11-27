package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

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


    private final RatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingService.class);

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Fait
    @RequestMapping("/rating/list")
    public String home(Model model) {

        List<Rating> ratingList = ratingService.findAll();
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
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    //Fait
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Rating rating = ratingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        ratingService.delete(ratingService.findById(id));
        List<Rating> ratingList = ratingService.findAll();
        model.addAttribute("ratings", ratingList);

        return "redirect:/rating/list";
    }
}
