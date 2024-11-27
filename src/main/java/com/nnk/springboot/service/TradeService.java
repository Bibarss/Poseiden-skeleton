package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Trade findById(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public void delete(Trade trade) {
        tradeRepository.deleteById(trade.getTradeId());
    }

    public boolean existsById(int id) {
        return tradeRepository.existsById(id);
    }
}

