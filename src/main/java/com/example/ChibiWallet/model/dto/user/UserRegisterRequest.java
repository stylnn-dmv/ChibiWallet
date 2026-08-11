package com.example.ChibiWallet.model.dto.user;

import com.example.ChibiWallet.model.entity.user.Country;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class UserRegisterRequest {

    String username;
    String password;
    Country country;
}
