package com.example.ChibiWallet.mapper.transaction;

import com.example.ChibiWallet.mapper.user.UserMapper;
import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.model.entity.transaction.Transaction;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionMapper {

    public static TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .uuid(transaction.getUuid())
                .owner(UserMapper.toUserDto(transaction.getOwner()))
                .sender(transaction.getSender())
                .receiver(transaction.getReceiver())
                .amount(transaction.getAmount())
                .balanceLeft(transaction.getBalanceLeft())
                .currency(transaction.getCurrency())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .failureReason(transaction.getFailureReason())
                .createdOn(transaction.getCreatedOn())
                .build();
    }

}
