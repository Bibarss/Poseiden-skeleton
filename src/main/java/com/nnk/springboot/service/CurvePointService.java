package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;


public interface CurvePointService {

    public List<CurvePoint> findAll();

    public CurvePoint findById(Integer id);

    public CurvePoint save(CurvePoint curvePoint) ;

    public void delete(CurvePoint curvePoint);

    public boolean existsById(int id);
}
