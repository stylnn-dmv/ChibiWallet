package com.example.ChibiWallet.web.wallet;


import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.wallet.WalletDto;
import com.example.ChibiWallet.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallets")
public class WalletController {

    private final UserService userService;

    public WalletController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ModelAndView getWallets(HttpSession session) {
        UserDto user = userService.getById((UUID) session.getAttribute("user_id"));
        List<WalletDto> wallets = user.getWallets();
        ModelAndView mav = new ModelAndView("wallets");
        mav.addObject("wallets", wallets);


        return new ModelAndView("wallets");
    }
}
