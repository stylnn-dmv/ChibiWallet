package com.example.ChibiWallet.model.dto.wallet;

import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.model.entity.wallet.WalletStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;


@Data
@Builder
public class WalletDto {
    private UUID id;
    private User owner;
    private WalletStatus status;
    private BigDecimal balance;
    private Currency currency;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
