package com.example.ChibiWallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.ChibiWallet.service.LoggedUserManagementService;

@Controller
public class MainController {

    private final LoggedUserManagementService loggedUserManagementService;

    public MainController(
            LoggedUserManagementService loggedUserManagementService
    ){
        this.loggedUserManagementService = loggedUserManagementService;
    }

    @GetMapping("/test")
    public String test() {
        return "home";
    }

    @GetMapping("/home")
    public String home(
            @RequestParam(required = false) String logout,
            Model model
    ){
        if(logout != null){
            loggedUserManagementService.setUsername(null);
        }
        String username = loggedUserManagementService.getUsername();

        if (username == null){
            return "redirect:/";
        }

        model.addAttribute("username",username);
        return "home.html";
    }


}
