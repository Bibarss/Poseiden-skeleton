package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TradeService {

    public List<Trade> findAll() ;

    public Trade findById(Integer id) ;

    public Trade save(Trade trade) ;

    public void delete(Trade trade) ;

    public boolean existsById(int id) ;
}

