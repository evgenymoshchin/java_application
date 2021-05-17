package com.gmail.evgenymoshchin.web.constants;

public interface ConfigConstant {
    String ADMIN_URL = "/users/get";
    String SALE_USER_URL = "/articles/get";
    String ACCESS_DENIED_URL = "/access-denied";
    String CUSTOMER_USER_URL = "/articles/get";
    String SECURE_API_USER_URL = "/";
    String LOGIN_PAGE_URL = "/login";
    String USERS_CONTROLLER_MAPPING_URL = "/users/**";
    String REVIEWS_CONTROLLER_MAPPING_URL = "/reviews/**";
    String DEFAULT_URL = "/";
    String API_URL_VALUE = "/api/**";
    String PROFILES_URL = "/profiles/**";
    String ARTICLES_URL = "/articles";
    String DELETE_ARTICLE_URL = "/delete-article-by-id";
    String ADD_ARTICLE_URL = "/add";
    String ITEMS_URL = "/items/**";
    String GET_ARTICLES_URL = "/get";
    String SHOW_ARTICLE_URL = "/show-article-by-id";
    String UPDATE_ARTICLE_URL = "/update";
}
