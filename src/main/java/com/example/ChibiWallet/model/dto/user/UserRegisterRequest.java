package com.example.ChibiWallet.model.dto.user;

import com.example.ChibiWallet.model.entity.user.Country;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
@Builder

public class UserRegisterRequest {

    @Size(min=6, message = "Username must be at least 6 characters")
    private String username;
    @Size(min=6, message = "Password must be at least 6 characters")
    private String password;
    @NotNull(message = "Country must not be null")
    private Country country;
}
