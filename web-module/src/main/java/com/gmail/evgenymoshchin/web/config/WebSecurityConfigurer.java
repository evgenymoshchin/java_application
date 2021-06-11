package com.gmail.evgenymoshchin.web.config;

import com.gmail.evgenymoshchin.web.config.handlers.AppUrlSuccessHandler;
import com.gmail.evgenymoshchin.web.constants.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ACCESS_DENIED_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ADD_ITEM_TO_ORDER_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ADD_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ARTICLES_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.CHANGE_REVIEW_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.COPY_ITEM_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.DEFAULT_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.DELETE_ARTICLE_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.DELETE_ITEM_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.GET_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.GET_ORDERS_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ITEMS_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.LOGIN_PAGE_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.ORDERS_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.PROFILES_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.REMOVE_REVIEW_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.REVIEWS_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.SHOW_ARTICLE_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.SHOW_ITEM_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.SHOW_ORDER_BY_ID_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.UPDATE_ARTICLE_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.UPDATE_ORDER_STATUS_URL;
import static com.gmail.evgenymoshchin.web.constants.ConfigConstant.USERS_CONTROLLER_MAPPING_URL;

@Configuration
@Order(2)
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
                .antMatchers(USERS_CONTROLLER_MAPPING_URL,
                        REVIEWS_URL + REMOVE_REVIEW_URL,
                        REVIEWS_URL + CHANGE_REVIEW_URL)
                .hasRole(RoleEnum.ADMINISTRATOR.name())
                .antMatchers(PROFILES_URL, REVIEWS_URL + ADD_URL, ITEMS_URL + ADD_ITEM_TO_ORDER_URL)
                .hasRole(RoleEnum.CUSTOMER_USER.name())
                .antMatchers(REVIEWS_URL + GET_URL)
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER_USER.name())
                .antMatchers(ARTICLES_URL + DELETE_ARTICLE_URL,
                        ARTICLES_URL + ADD_URL,
                        ARTICLES_URL + UPDATE_ARTICLE_URL,
                        ORDERS_URL + SHOW_ORDER_BY_ID_URL,
                        ORDERS_URL + UPDATE_ORDER_STATUS_URL,
                        ITEMS_URL + SHOW_ITEM_URL,
                        ITEMS_URL + DELETE_ITEM_URL,
                        ITEMS_URL + COPY_ITEM_URL
                )
                .hasRole(RoleEnum.SALE_USER.name())
                .antMatchers(ARTICLES_URL + GET_URL,
                        ARTICLES_URL + SHOW_ARTICLE_URL,
                        ORDERS_URL + GET_ORDERS_URL,
                        ITEMS_URL + GET_URL)
                .hasAnyRole(RoleEnum.CUSTOMER_USER.name(), RoleEnum.SALE_USER.name())
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
