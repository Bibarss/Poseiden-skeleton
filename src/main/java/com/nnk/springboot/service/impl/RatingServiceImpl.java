package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
    }

    @Transactional
    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Transactional
    @Override
    public void delete(Rating rating) {
        ratingRepository.deleteById(rating.getId());
    }

    @Override
    public boolean existsById(int id) {
        return ratingRepository.existsById(id);
    }
}
