package com.example.ChibiWallet.service.wallet;

import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.model.entity.wallet.Wallet;
import com.example.ChibiWallet.model.entity.wallet.WalletStatus;
import com.example.ChibiWallet.repository.wallet.WalletRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Service
public class WalletService {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }
    public Wallet createDefaultWallet(User user){


        Wallet wallet =  Wallet.builder()
                .owner(user)
                .currency(Currency.getInstance("EUR"))
                .balance(BigDecimal.valueOf(20.00))
                .status(WalletStatus.ACTIVE)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        walletRepository.save(wallet);
        return wallet;

    }
}
