package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {

    public List<BidList> findAll();

    public BidList findById(Integer id) ;

    public BidList save(BidList bidList);

    public void delete(BidList bidList);

    public boolean existsById(int id);
}
