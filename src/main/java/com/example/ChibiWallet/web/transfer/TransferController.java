package com.example.ChibiWallet.web.transfer;


import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.model.dto.transfer.TransferRequest;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.service.user.UserService;
import com.example.ChibiWallet.service.wallet.WalletService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/transfers")
public class TransferController {
    private final UserService userService;
    private final WalletService walletService;

    public TransferController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService=walletService;
    }

    @GetMapping
    public ModelAndView getTransfersPage(HttpSession session) {
        UserDto user = userService.getById((UUID)  session.getAttribute("user_id"));
        ModelAndView mav = new ModelAndView("transfers");
        mav.addObject("user", user);
        mav.addObject("transferRequest", TransferRequest.builder().build());

        return mav;
    }


    @PostMapping
    public ModelAndView initiateTransfer(@Valid TransferRequest transferRequest, BindingResult bindingResult,HttpSession session) {
        UserDto user = userService.getById((UUID) session.getAttribute("user_id"));

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("transfers");
            mav.addObject("transferRequest", transferRequest);
            mav.addObject("user", user);
            return mav;
        }

        TransactionDto transaction = walletService.transferFunds(user,transferRequest);
        return new ModelAndView("redirect:/transactions/" + transaction.getUuid());
    }
}
