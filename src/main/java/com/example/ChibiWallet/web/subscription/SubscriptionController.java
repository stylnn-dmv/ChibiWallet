package com.example.ChibiWallet.web.subscription;

import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final UserService userService;

    public SubscriptionController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ModelAndView subscriptions() {
        return new ModelAndView("upgrade");
    }

    @GetMapping("/history")
    public ModelAndView getSubscriptionHistoryPage() {
        UserDto user = userService.getById("2662debf-73c9-4d54-9c30-a0716fec2629");


        ModelAndView mav = new ModelAndView("subscriptions-history");
        mav.addObject("user", user);
        return mav;
    }
}
