package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;

import java.util.List;


public interface RuleNameService {

    public List<RuleName> findAll() ;

    public RuleName findById(Integer id);

    public RuleName save(RuleName ruleName);

    public void delete(RuleName ruleName) ;

    public boolean existsById(int id);
}

