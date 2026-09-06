package com.example.ChibiWallet.web.wallet;


import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.wallet.WalletDto;
import com.example.ChibiWallet.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/wallets")
public class WalletController {

    private final UserService userService;

    public WalletController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ModelAndView getWallets() {
        UserDto user = userService.getById("2662debf-73c9-4d54-9c30-a0716fec2629");
        List<WalletDto> wallets = user.getWallets();
        ModelAndView mav = new ModelAndView("wallets");
        mav.addObject("wallets", wallets);


        return new ModelAndView("wallets");
    }
}
