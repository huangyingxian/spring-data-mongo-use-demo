package com.example.demo.service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepositoryImpl;
//import com.example.demo.domain.repository.UserRepositoryImplJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

//    @Autowired
//    private UserRepositoryImplJpa userRepositoryImplJpa;

    public User getUserByUserName(String username){
        log.info("getUserByUserName receive username {}", username);
        User user = userRepositoryImpl.findUserByUserName(username);
        return user;
    }

    public void addUserByUserName(User user){
        userRepositoryImpl.saveUser(user);
    }

//    public List<User> getAllUser(){
//        List<User> users =  userRepositoryImplJpa.findAll();
//        return users;
//    }
}
