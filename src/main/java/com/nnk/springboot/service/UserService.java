package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;


public interface UserService {

    public User findByUsername(String username);

    public List<User> findAll();

    public User findById(Integer id);

    public User save(User user);

    public void delete(User user);

    public boolean existsById(int id);
}
