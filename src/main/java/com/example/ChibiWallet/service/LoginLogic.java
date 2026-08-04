package com.example.ChibiWallet.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LoginLogic {

    private final LoggedUserManagementService loggedUserManagementService;
    private String username;
    private String password;

    public LoginLogic( LoggedUserManagementService loggedUserManagementService){
        this.loggedUserManagementService =loggedUserManagementService;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public boolean login(){
        String username = this.getUsername();
        String password = this.getPassword();

        boolean loginResult = false;
        if("s".equals(username) && "1".equals(password)) {
            loginResult = true;
            loggedUserManagementService.setUsername(username);
        }
        return loginResult;
    }
}
