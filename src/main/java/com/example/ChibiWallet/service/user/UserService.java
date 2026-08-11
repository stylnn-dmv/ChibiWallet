package com.example.ChibiWallet.service.user;

import com.example.ChibiWallet.mapper.user.UserMapper;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.user.UserRegisterRequest;
import com.example.ChibiWallet.model.entity.subscription.Subscription;
import com.example.ChibiWallet.model.entity.user.User;

import com.example.ChibiWallet.model.entity.wallet.Wallet;
import com.example.ChibiWallet.repository.user.UserRepository;
import com.example.ChibiWallet.service.subscription.SubscriptionService;
import com.example.ChibiWallet.service.wallet.WalletService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private SubscriptionService subscriptionService;
    private WalletService walletService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,SubscriptionService subscriptionService ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionService = subscriptionService;
    }


    public UserDto register(UserRegisterRequest userRegisterRequest) {
        userRepository.findByUsername(userRegisterRequest.getUsername()).ifPresent(user -> {throw new RuntimeException("User with this username already exists!");} );


        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodedPassword);


        User userEntity = UserMapper.toUserEntity(userRegisterRequest);


        Subscription defualtSubscription = subscriptionService.createDefaultSubscription(userEntity);
        userEntity.setSubscriptions(List.of(defualtSubscription));

        Wallet defaultWallet = walletService.createDefaultWallet(userEntity);
        userEntity.setWallets(List.of(defaultWallet));

        userRepository.save(userEntity);

        return UserMapper.toUserDto(userEntity);
    }
}
