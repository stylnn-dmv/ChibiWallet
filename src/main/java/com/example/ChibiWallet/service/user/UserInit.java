package com.example.ChibiWallet.service.user;

import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.model.dto.user.UserRegisterRequest;
import com.example.ChibiWallet.model.entity.user.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserInit implements CommandLineRunner {
    private UserService userService;

    public UserInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<UserDto> users = userService.findAll();
        if (!users.isEmpty()) {
            return;
        }
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .username("defaultUser")
                .password("defaultPassword")
                .country(Country.BULGARIA)
                .build();

        userService.register(userRegisterRequest);

        log.info("Default user(%s) registered successfully".formatted(userRegisterRequest.getUsername()));
    }
}
