package com.example.ChibiWallet.model.dto.subscription;

import com.example.ChibiWallet.model.entity.subscription.SubscriptionPeriod;
import com.example.ChibiWallet.model.entity.subscription.SubscriptionStatus;
import com.example.ChibiWallet.model.entity.subscription.SubscriptionType;
import com.example.ChibiWallet.model.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SubscriptionDto {

    private UUID id;
    private User owner;
    private SubscriptionPeriod period;
    private SubscriptionStatus status;
    private SubscriptionType type;
    private BigDecimal price;
    private boolean renewalAllowed;
    private LocalDateTime createdOn;
    private LocalDateTime completedOn;
}
