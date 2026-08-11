package com.example.ChibiWallet.mapper.subscription;

import com.example.ChibiWallet.model.dto.subscription.SubscriptionDto;
import com.example.ChibiWallet.model.entity.subscription.Subscription;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SubscriptionMapper {

    public static SubscriptionDto toDto(Subscription subscription){
        if(subscription == null){
            return null;
        }

        return SubscriptionDto.builder()
                .id(subscription.getId())
                .owner(subscription.getOwner())
                .status(subscription.getStatus())
                .period(subscription.getPeriod())
                .type(subscription.getType())
                .price(subscription.getPrice())
                .renewalAllowed(subscription.isRenewalAllowed())
                .createdOn(subscription.getCreatedOn())
                .completedOn(subscription.getCreatedOn())
                .build();
    }
}
