package com.example.ChibiWallet.service.transaction;

import com.example.ChibiWallet.mapper.transaction.TransactionMapper;
import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.model.entity.transaction.Transaction;
import com.example.ChibiWallet.model.entity.transaction.TransactionStatus;
import com.example.ChibiWallet.model.entity.transaction.TransactionType;
import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.repository.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createNewTransaction(User owner,
                                            String sender,
                                            String receiver,
                                            BigDecimal amount,
                                            BigDecimal balanceLeft,
                                            Currency currency,
                                            TransactionType transactionType,
                                            TransactionStatus transactionStatus,
                                            String description,
                                            String failureReason
                                            ) {


        Transaction transaction =Transaction.builder()
                .owner(owner)
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .balanceLeft(balanceLeft)
                .currency(currency)
                .type(transactionType)
                .status(transactionStatus)
                .description(description)
                .failureReason(failureReason)
                .createdOn(java.time.LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    public TransactionDto getById(String id) {
        Transaction transaction =transactionRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id ));
        return TransactionMapper.toDto(transaction);
    }
}
