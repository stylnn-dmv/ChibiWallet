package com.example.ChibiWallet.model.dto.user;

import com.example.ChibiWallet.model.dto.subscription.SubscriptionDto;
import com.example.ChibiWallet.model.dto.wallet.WalletDto;
import com.example.ChibiWallet.model.entity.subscription.Subscription;
import com.example.ChibiWallet.model.entity.user.Country;
import com.example.ChibiWallet.model.entity.user.UserRole;
import com.example.ChibiWallet.model.entity.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String email;
    private UserRole role;
    private Country country;
    private boolean isActive;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<SubscriptionDto> subscriptions;
    private List<WalletDto> wallets;

}
