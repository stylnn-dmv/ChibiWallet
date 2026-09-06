package com.example.ChibiWallet.web.transfer;


import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.model.dto.transfer.TransferRequest;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.service.user.UserService;
import com.example.ChibiWallet.service.wallet.WalletService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getTransfersPage() {
        UserDto user = userService.getById("2662debf-73c9-4d54-9c30-a0716fec2629");
        ModelAndView mav = new ModelAndView("transfers");
        mav.addObject("user", user);
        mav.addObject("transferRequest", TransferRequest.builder().build());

        return mav;
    }


    @PostMapping
    public ModelAndView initiateTransfer(@Valid TransferRequest transferRequest, BindingResult bindingResult) {
        UserDto user = userService.getById("2662debf-73c9-4d54-9c30-a0716fec2629");

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("transfers");
            mav.addObject("transferRequest", transferRequest);
            mav.addObject("user", user);
            return mav;
        }



        TransactionDto transaction = walletService.transferFunds(user,transferRequest);
        return new ModelAndView("redirect:/transactions/" + transaction.getId());
    }
}
