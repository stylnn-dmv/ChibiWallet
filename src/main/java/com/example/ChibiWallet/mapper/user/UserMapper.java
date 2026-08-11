package com.example.ChibiWallet.mapper.user;

import com.example.ChibiWallet.mapper.subscription.SubscriptionMapper;
import com.example.ChibiWallet.mapper.wallet.WalletMapper;
import com.example.ChibiWallet.model.dto.subscription.SubscriptionDto;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.user.UserRegisterRequest;
import com.example.ChibiWallet.model.dto.wallet.WalletDto;
import com.example.ChibiWallet.model.entity.user.User;
import com.example.ChibiWallet.model.entity.user.UserRole;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class UserMapper {

    public static User toUserEntity(UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return null;
        }
        return User.builder()
                .username(userRegisterRequest.getUsername())
                .password(userRegisterRequest.getPassword())
                .country(userRegisterRequest.getCountry())
                .role(UserRole.USER)
                .isActive(true)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

    }

    public static UserDto toUserDto(User user){
        if(user == null){
            return null;
        }
        List<SubscriptionDto> subscriptionDtosList =user
                .getSubscriptions()
                .stream().map(SubscriptionMapper::toDto)
                .toList();

        List<WalletDto> walletDtoList =user
                .getWallets()
                .stream().map(WalletMapper::toDto)
                .toList();

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicture(user.getProfilePicture())
                .email(user.getEmail())
                .country(user.getCountry())
                .role(user.getRole())
                .isActive(user.isActive())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .subscriptions(subscriptionDtosList)
                .wallets(walletDtoList)
                .build();



    }
}
