package com.example.ChibiWallet.model.dto.transaction;

import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.entity.transaction.TransactionStatus;
import com.example.ChibiWallet.model.entity.transaction.TransactionType;
import com.example.ChibiWallet.model.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Data
@Builder
public class TransactionDto {

    private UUID uuid;
    private UserDto owner;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private BigDecimal balanceLeft;
    private Currency currency;
    private TransactionType type;
    private TransactionStatus status;
    private String description;
    private String failureReason;
    private LocalDateTime createdOn;

}
