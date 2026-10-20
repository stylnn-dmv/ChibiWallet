package com.example.ChibiWallet.config;

import com.example.ChibiWallet.security.SessionInterceptor;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.PathPatternRequestMatcherBuilderFactoryBean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

            http
                    .authorizeHttpRequests(matchers -> matchers
                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                    .permitAll()
                                    .requestMatchers("/","/login","/register","/error")
                                    .permitAll()
                                    .requestMatchers( "/reports/**")
                                    .hasRole("ADMIN")
                                    .anyRequest()
                                    .authenticated()


                            )
                    .formLogin(form -> form
                            .loginPage("/login")
                            .defaultSuccessUrl("/home",true)
                            .failureUrl("/login?error")
                            .permitAll())

                    .logout(logout -> logout
                            .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.valueOf("GET"),"/logout"))
                            .logoutSuccessUrl("/")
                    );


            return http.build();
    }



//    private final SessionInterceptor sessionInterceptor;
//
//    public WebMvcConfiguration(SessionInterceptor sessionInterceptor) {
//        this.sessionInterceptor = sessionInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(sessionInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**", "/images/**");
//    }

}
