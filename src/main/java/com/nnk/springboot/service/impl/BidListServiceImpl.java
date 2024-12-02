package com.nnk.springboot.service.impl;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl  implements BidListService {


    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
    }

    @Transactional
    @Override
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Transactional
    @Override
    public void delete(BidList bidList) {
        bidListRepository.deleteById(bidList.getId());
    }

    @Override
    public boolean existsById(int id) {
        return bidListRepository.existsById(id);
    }
}
