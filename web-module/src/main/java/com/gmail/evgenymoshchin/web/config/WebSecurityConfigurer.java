package com.gmail.evgenymoshchin.web.config;

import com.gmail.evgenymoshchin.web.config.handlers.AppUrlSuccessHandler;
import com.gmail.evgenymoshchin.web.constants.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ACCESS_DENIED_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.DEFAULT_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.LOGIN_PAGE_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.REVIEWS_CONTROLLER_MAPPING_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.USERS_CONTROLLER_MAPPING_URL;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfigurer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(USERS_CONTROLLER_MAPPING_URL, REVIEWS_CONTROLLER_MAPPING_URL)
                .hasRole(RoleEnum.ADMINISTRATOR.name())
                .antMatchers("/articles/**", "/profiles/**")
                .hasRole(RoleEnum.CUSTOMER_USER.name())
                .antMatchers(LOGIN_PAGE_URL, DEFAULT_URL, ACCESS_DENIED_URL)
                .permitAll()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE_URL)
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
}
