package com.example.ChibiWallet.service.user;

import com.example.ChibiWallet.mapper.user.UserMapper;
import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.user.UserLoginRequest;
import com.example.ChibiWallet.model.dto.user.UserRegisterRequest;
import com.example.ChibiWallet.model.entity.subscription.Subscription;
import com.example.ChibiWallet.model.entity.user.EditUserRequest;
import com.example.ChibiWallet.model.entity.user.User;

import com.example.ChibiWallet.model.entity.wallet.Wallet;
import com.example.ChibiWallet.repository.user.UserRepository;
import com.example.ChibiWallet.service.subscription.SubscriptionService;
import com.example.ChibiWallet.service.wallet.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.boot.actuate.info.EnvironmentInfoContributor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional

public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private SubscriptionService subscriptionService;
    private WalletService walletService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SubscriptionService subscriptionService, WalletService walletService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionService = subscriptionService;
        this.walletService = walletService;

    }
    public UserDto login(UserLoginRequest userLoginRequest){
            Optional<User> optionalUser = userRepository.findByUsername(userLoginRequest.getUsername());

            if(optionalUser.isEmpty() || !passwordEncoder.matches(userLoginRequest.getPassword(),optionalUser.get().getPassword())){
                throw new RuntimeException("Username or password mismatch!");
            }
            return UserMapper.toUserDto(optionalUser.get());
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

    public List<UserDto> findAll(){
        return userRepository.findAll().stream().map(UserMapper::toUserDto).toList();
    }

    public UserDto getById(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("User not found!"));
        return UserMapper.toUserDto(user);
    }

    public UserDto update(String id, EditUserRequest editUserRequest) {
        User entity = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("User not found!"));

        entity.setFirstName(editUserRequest.getFirstName());
        entity.setLastName(editUserRequest.getLastName());
        entity.setEmail(editUserRequest.getEmail());
        entity.setProfilePicture(editUserRequest.getProfilePicture());


        User updatedUser = userRepository.save(entity);
        return UserMapper.toUserDto(updatedUser);
    }
}
