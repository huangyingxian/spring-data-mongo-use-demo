package com.example.demo.controller;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class helloController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "hello world,hhahah,你好呀";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public User getUserDetail(@RequestParam(value = "username") String username) {
        User user = userService.getUserByUserName(username);
        return user;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @ResponseBody
    public String addUserDetail() {
        User u = new User();
        u.setId(11111L);
        u.setUserName("hyx");
        u.setPassWord("123456");
        userService.addUserByUserName(u);
        return "SUCCESS";
    }

//    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
//    @ResponseBody
//    public List<User> getAll() {
//        List<User> users = userService.getAllUser();
//        return users;
//    }
}
