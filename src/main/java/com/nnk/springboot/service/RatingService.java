package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {


    public List<Rating> findAll();

    public Rating findById(Integer id);

    public Rating save(Rating rating);

    public void delete(Rating rating) ;

    public boolean existsById(int id) ;
}


