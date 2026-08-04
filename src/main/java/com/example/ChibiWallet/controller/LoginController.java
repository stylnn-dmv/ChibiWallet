package com.example.ChibiWallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.ChibiWallet.service.LoginLogic;


@Controller
public class LoginController {

    private final LoginLogic loginLogic;

    public LoginController(
            LoginLogic loginLogic
    ){
        this.loginLogic = loginLogic;
    }

    @GetMapping("/")
    public String loginGet(){
        return "login.html";
    }

    @PostMapping("/")
    public String loginPost(
            @RequestParam String username,
            @RequestParam String password,
            Model model)
    {
        loginLogic.setUsername(username);
        loginLogic.setPassword(password);


       if(loginLogic.login()){
           model.addAttribute("message","You are now logged in");
           return "redirect:/home";
       }else {
           // could add time out and suggestion for a forget password button
           model.addAttribute("message","Login failed. Try again");
           return "login.html";
       }

    }
}
