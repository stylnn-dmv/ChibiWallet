package com.example.ChibiWallet.service.subscription;


import com.example.ChibiWallet.model.entity.subscription.Subscription;
import com.example.ChibiWallet.model.entity.subscription.SubscriptionPeriod;
import com.example.ChibiWallet.model.entity.subscription.SubscriptionStatus;
import com.example.ChibiWallet.model.entity.subscription.SubscriptionType;
import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.repository.subscription.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SubscriptionService {

    SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createDefaultSubscription(User user){
        LocalDateTime now = LocalDateTime.now();

        Subscription subscription = Subscription.builder()
                .owner(user)
                .period(SubscriptionPeriod.MONTHLY)
                .status(SubscriptionStatus.ACTIVE)
                .type(SubscriptionType.DEFAULT)
                .price(BigDecimal.valueOf(0.00))
                .completedOn(now.plusMonths(1))
                .renewalAllowed(true)
                .createdOn(now)
                .build();

        subscriptionRepository.save(subscription);
        return  subscription;
    }

}
