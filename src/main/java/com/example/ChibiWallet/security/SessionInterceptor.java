package com.example.ChibiWallet.security;


import com.example.ChibiWallet.model.dto.user.UserDto;
import com.example.ChibiWallet.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;
import java.util.UUID;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Set<String> UNAUTHENTICATED_ENDPOINTS = Set.of("/","/login","/register","/logout","/error");
    //"/users","/reports" -> later to add admin panel
    private static final Set<String> ADMIN_ENDPOINTS = Set.of();
    private final UserService userService;

    @Autowired
    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String endpoint = request.getServletPath();
        if(UNAUTHENTICATED_ENDPOINTS.contains(endpoint)){
            return true;
        }

        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/login");
            return false;
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        if(userId == null){
            session.invalidate();
            response.sendRedirect("/login");
            return false;
        }
        UserDto user = userService.getById(userId);

        if(!user.isActive()){
            session.invalidate();
            response.sendRedirect("/login");
            return false;
        }

        if(ADMIN_ENDPOINTS.contains(endpoint) && !user.getRole().name().equals("ADMIN")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("You are not allowed to access this resource");
            return false;
        }


        return true;
    }



}
