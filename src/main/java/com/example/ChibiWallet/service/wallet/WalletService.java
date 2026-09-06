package com.example.ChibiWallet.service.wallet;

import com.example.ChibiWallet.mapper.transaction.TransactionMapper;
import com.example.ChibiWallet.mapper.user.UserMapper;
import com.example.ChibiWallet.model.dto.transaction.TransactionDto;
import com.example.ChibiWallet.model.dto.transfer.TransferRequest;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.entity.transaction.Transaction;
import com.example.ChibiWallet.model.entity.transaction.TransactionStatus;
import com.example.ChibiWallet.model.entity.transaction.TransactionType;
import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.model.entity.wallet.Wallet;
import com.example.ChibiWallet.model.entity.wallet.WalletStatus;
import com.example.ChibiWallet.repository.transaction.TransactionRepository;
import com.example.ChibiWallet.repository.wallet.WalletRepository;

import com.example.ChibiWallet.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WalletService {

    private WalletRepository walletRepository;
    private TransactionService transactionService;

    public WalletService(WalletRepository walletRepository, TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    public Wallet createDefaultWallet(User user) {


        Wallet wallet = Wallet.builder()
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

    public Transaction topUp(UUID walletId, BigDecimal amount) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isEmpty()) {
            throw new RuntimeException("Wallet with id %s not found".formatted(walletId));
        }

        Wallet wallet = optionalWallet.get();
        String description = "Top up wallet with %.2f".formatted(amount);
        if (wallet.getStatus().equals(WalletStatus.INACTIVE)) {
            return transactionService.createNewTransaction(wallet.getOwner(),
                    "Some Sender",
                    wallet.getId().toString()
                    , amount, wallet.getBalance()
                    , wallet.getCurrency(),
                    TransactionType.DEPOSIT,
                    TransactionStatus.FAILED,
                    description,
                    "Wallet is inactive");
        }
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedOn(LocalDateTime.now());
        walletRepository.save(wallet);

        return transactionService.createNewTransaction(
                wallet.getOwner(),
                "Some Sender",
                wallet.getId().toString()
                , amount, wallet.getBalance()
                , wallet.getCurrency(),
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCEEDED,
                description,
                null);
    }

    public Transaction charge(User user, UUID walletId, BigDecimal amount, String chargeDescription) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isEmpty()) {
            throw new RuntimeException("Wallet with id %s not found".formatted(walletId));
        }

        Wallet wallet = optionalWallet.get();

        if (wallet.getStatus().equals(WalletStatus.INACTIVE)) {
            return transactionService.createNewTransaction(
                    wallet.getOwner(),
                    wallet.getId().toString(),
                    user.getUsername(),
                    amount,
                    wallet.getBalance(),
                    wallet.getCurrency(),
                    TransactionType.WITHDRAW,
                    TransactionStatus.FAILED,
                    chargeDescription,
                    "Wallet is inactive"
            );
        }
        if (wallet.getBalance().compareTo(amount) < 0) {
            return transactionService.createNewTransaction(
                    wallet.getOwner(),
                    wallet.getId().toString(),
                    user.getUsername(),
                    amount,
                    wallet.getBalance(),
                    wallet.getCurrency(),
                    TransactionType.WITHDRAW,
                    TransactionStatus.FAILED,
                    chargeDescription,
                    "Insufficient funds"
            );
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setUpdatedOn(LocalDateTime.now());
        walletRepository.save(wallet);

        return transactionService.createNewTransaction(wallet.getOwner(),
                wallet.getId().toString(),
                user.getUsername(),
                amount,
                wallet.getBalance(),
                wallet.getCurrency(),
                TransactionType.WITHDRAW,
                TransactionStatus.SUCCEEDED,
                chargeDescription,
                null

        );
    }

    public TransactionDto transferFunds(UserDto senderDto, TransferRequest transferRequest) {
        User sender = UserMapper.toEntity(senderDto);
        Wallet senderWallet = walletRepository.findById(transferRequest.getFromWalletsId())
                .orElseThrow(() -> new RuntimeException("Wallet with id %s not found".formatted(transferRequest.getFromWalletsId())));

        Optional<Wallet> receiver = walletRepository.findAllByOwner_Username(transferRequest.getToUsername())
                .stream()
                .filter(wallet -> wallet.getStatus().equals(WalletStatus.ACTIVE))
                .findFirst();

        if (receiver.isEmpty()) {
            Transaction transaction = transactionService.createNewTransaction(
                    sender,
                    senderWallet.getId().toString(),
                    transferRequest.getToUsername(),
                    transferRequest.getAmount(),
                    senderWallet.getBalance(),
                    senderWallet.getCurrency(),
                    TransactionType.WITHDRAW,
                    TransactionStatus.FAILED,
                    "Transfer to %s failed.".formatted(transferRequest.getToUsername()),
                    "Receiver does not have an active wallet.Please ask the receiver to create an active wallet and try again."
            );
            return TransactionMapper.toDto(transaction);
        }

        Transaction withdrawal = charge(sender, senderWallet.getId(),
                transferRequest.getAmount(),
                "Transfer to %s".formatted(transferRequest.getToUsername())

        );

        if(withdrawal.getStatus().equals(TransactionStatus.FAILED)) {
            return TransactionMapper.toDto(withdrawal);
        }

        Wallet receiverWallet = receiver.get();
        receiverWallet.setBalance(receiverWallet.getBalance().add(transferRequest.getAmount()));
        walletRepository.save(receiverWallet);

        Transaction transaction = transactionService.createNewTransaction(
                receiverWallet.getOwner(),
                senderWallet.getId().toString(),
                transferRequest.getToUsername(),
                transferRequest.getAmount(),
                receiverWallet.getBalance(),
                receiverWallet.getCurrency(),
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCEEDED,
                "Transfer to %s.".formatted(transferRequest.getToUsername()),
                null
        );
        return TransactionMapper.toDto(transaction);


    }


//    public void createNewWallet(User user){
//        Wallet wallet = Wallet.builder()
//                .owner(user)
//                .currency(Currency.getInstance("EUR"))
//                .balance(BigDecimal.valueOf(0.00))
//                .status(WalletStatus.ACTIVE)
//                .createdOn(LocalDateTime.now())
//                .updatedOn(LocalDateTime.now())
//                .build();
//
//        walletRepository.save(wallet);
//    }

}
