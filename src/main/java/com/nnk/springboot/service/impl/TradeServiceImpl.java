package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));
    }

    @Transactional
    @Override
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Transactional
    @Override
    public void delete(Trade trade) {
        tradeRepository.deleteById(trade.getTradeId());
    }

    @Override
    public boolean existsById(int id) {
        return tradeRepository.existsById(id);
    }
}

