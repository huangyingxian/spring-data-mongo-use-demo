package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;

public interface UserRepository  {

    void saveUser(User user);
    User findUserByUserName(String userName);
    long updateUser(User user);
    void deleteUserById(Long id);
}
