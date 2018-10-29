package com.application.catny.controller;

import com.application.catny.entity.User;
import com.application.catny.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/userlogin",method = RequestMethod.POST)
    String userLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        User user = userMapper.login(username, password);
        if(user==null){
            return "redirect:login";
        }else {
            return "home";
        }
    }
}
