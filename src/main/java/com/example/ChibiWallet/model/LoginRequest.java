package com.example.ChibiWallet.model;

import java.math.BigDecimal;

public class LoginRequest {
    private String username;
    private String password;
    private BigDecimal id;

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
}
