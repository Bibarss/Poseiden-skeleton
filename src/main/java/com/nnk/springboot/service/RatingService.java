package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating findById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void delete(Rating rating) {
        ratingRepository.deleteById(rating.getId());
    }

    public boolean existsById(int id) {
        return ratingRepository.existsById(id);
    }
}


