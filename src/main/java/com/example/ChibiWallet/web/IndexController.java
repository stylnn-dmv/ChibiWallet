package com.example.ChibiWallet.web;


import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.user.UserLoginRequest;
import com.example.ChibiWallet.model.dto.user.UserRegisterRequest;
import com.example.ChibiWallet.model.entity.user.Country;
import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
    private UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        UserLoginRequest userLoginRequest = UserLoginRequest.builder().build();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("userLoginRequest", userLoginRequest);
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login (@Valid UserLoginRequest userLoginRequest,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView mav = new ModelAndView();
            mav.addObject("userLoginRequest", userLoginRequest);
            mav.setViewName("login");
            return mav;
        }
        UserDto userDto = userService.login(userLoginRequest);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("user", userDto);

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage(){
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder().build();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        mav.addObject("userRegisterRequest", userRegisterRequest);
        mav.addObject("countries", Country.values());

        return mav;
    }

    @PostMapping("/register")
    public ModelAndView Register(@Valid UserRegisterRequest userRegisterRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            ModelAndView mav = new ModelAndView();
            mav.addObject("userRegisterRequest",userRegisterRequest);
            mav.addObject("countries", Country.values());
            mav.setViewName("register");
            return mav;
        }
        userService.register(userRegisterRequest);
        return new ModelAndView("redirect:/login");
    }





    @GetMapping("/home")
    public ModelAndView getHomePage(){
        return new ModelAndView("home");
    }


}
