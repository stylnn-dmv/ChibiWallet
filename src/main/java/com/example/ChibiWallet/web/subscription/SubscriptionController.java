package com.example.ChibiWallet.web.subscription;

import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.service.user.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;


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
    public ModelAndView getSubscriptionHistoryPage(HttpSession session) {

        UUID id = (UUID) session.getAttribute("user_id");
        UserDto user = userService.getById(id);


        ModelAndView mav = new ModelAndView("subscriptions-history");
        mav.addObject("user", user);
        return mav;
    }
}
