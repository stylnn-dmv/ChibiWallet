package com.example.ChibiWallet.model.dto.transfer;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class TransferRequest {


    @NotNull
    private UUID fromWalletsId;
    @NotNull
    private String toUsername;
    @NotNull
    @Positive
    private BigDecimal amount;
}
