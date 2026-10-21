package com.example.ChibiWallet.web.wallet;


import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.wallet.WalletDto;
import com.example.ChibiWallet.model.entity.transaction.Transaction;
import com.example.ChibiWallet.service.user.AuthenticationDetailsData;
import com.example.ChibiWallet.service.user.UserService;
import com.example.ChibiWallet.service.wallet.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallets")
public class WalletController {

    private final UserService userService;
    private final WalletService walletService;

    public WalletController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }


    @GetMapping
    public ModelAndView getWallets(@AuthenticationPrincipal AuthenticationDetailsData principal) {
        UserDto user = userService.getById(principal.getId());
        List<WalletDto> wallets = user.getWallets();
        ModelAndView mav = new ModelAndView("wallets");
        mav.addObject("wallet", wallets);
        return mav;
    }

    @PutMapping("/{walletId}/balance/top-up")
    public ModelAndView topUpWallet(@PathVariable String walletId) {
        TransactionDto transaction = walletService.topUp(UUID.fromString(walletId), BigDecimal.valueOf(20));
        return new ModelAndView("redirect:/transactions/" + transaction.getUuid());
    }
}
