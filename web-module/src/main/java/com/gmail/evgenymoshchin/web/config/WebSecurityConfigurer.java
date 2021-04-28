package com.gmail.evgenymoshchin.web.config;

import com.gmail.evgenymoshchin.web.config.handlers.AppUrlSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.evgenymoshchin.web.config.handlers.ConfigConstant.ACCESS_DENIED_URL;
import static com.gmail.evgenymoshchin.web.config.handlers.ConfigConstant.ADMIN_VALUE;
import static com.gmail.evgenymoshchin.web.config.handlers.ConfigConstant.USER_VALUE;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/get")
                .hasRole(ADMIN_VALUE)
                .antMatchers("/items/get", "/items/add", "/items/show-item-with-shop", "/api/items")
                .hasRole(USER_VALUE)
                .antMatchers("/login", "/users/add", "/", ACCESS_DENIED_URL)
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new AppUrlSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage(ACCESS_DENIED_URL)
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
