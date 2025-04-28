package com.example.project3.Config;

import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/customer/get-my-customers","/api/v1/customer/add-customer","/api/v1/customer/update-customer/{customerId}",
                                 "/api/v1/customer/delete-customer/{customerId}","/api/v1/account/my-accounts","/api/v1/account/details/{accountId}",
                                 "/api/v1/account/deposit/{accountId}","/api/v1/account/withdraw/{accountId}","/api/v1/account/transfer").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/create").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                .requestMatchers("/api/v1/account/block/{accountId}","/api/v1/employee/**").hasAuthority("EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return httpSecurity.build();

        // .requestMatchers("/api/v1/auth/**").hasAnyAuthority("USER","ADMIN")

        // requestMatcher for all users like (user / admin / customer...etc)
        // without duplicate


    }
}

