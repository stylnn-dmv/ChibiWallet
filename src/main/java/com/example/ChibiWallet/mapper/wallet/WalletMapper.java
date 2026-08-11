package com.example.ChibiWallet.mapper.wallet;
import com.example.ChibiWallet.model.dto.wallet.WalletDto;
import com.example.ChibiWallet.model.entity.wallet.Wallet;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WalletMapper {
    public static WalletDto toDto(Wallet wallet){

        if(wallet == null){
            return null;
        }


       return WalletDto.builder()
                .id(wallet.getId())
                .owner(wallet.getOwner())
                .status(wallet.getStatus())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .createdOn(wallet.getCreatedOn())
                .updatedOn(wallet.getUpdatedOn())
                .build();
    }

}
