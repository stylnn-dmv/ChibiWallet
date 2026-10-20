package com.example.ChibiWallet.web.transaction;

import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.repository.transaction.TransactionRepository;
import com.example.ChibiWallet.service.transaction.TransactionService;
import com.example.ChibiWallet.service.user.AuthenticationDetailsData;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping()
    public ModelAndView showAllTransactions(@AuthenticationPrincipal AuthenticationDetailsData principal) {

        ModelAndView mav = new ModelAndView("transactions");
        mav.addObject("transactions", transactionService.getAllByOwnerId(principal.getId()));
        return mav;
    }


    @GetMapping("/{id}")
    public ModelAndView showTransaction(@PathVariable String id) {

        TransactionDto transaction = transactionService.getById(id);

        ModelAndView mav = new ModelAndView("transaction-result");
        mav.addObject("transaction",transaction);
        return mav;

    }


}
