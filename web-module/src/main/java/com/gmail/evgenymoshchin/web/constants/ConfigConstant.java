package com.gmail.evgenymoshchin.web.constants;

public interface ConfigConstant {
    String ADMIN_URL = "/users/get";
    String SALE_USER_URL = "/items/get";
    String ACCESS_DENIED_URL = "/access-denied";
    String ADMIN_VALUE = "ADMINISTRATOR";
    String CUSTOMER_USER_URL = "/reviews/get";
    String SECURE_API_USER_URL = "/";
    String LOGIN_PAGE_URL = "/login";
    String USERS_CONTROLLER_MAPPING_URL = "/users/**";
    String REVIEWS_CONTROLLER_MAPPING_URL = "/reviews/**";
    String DEFAULT_URL = "/";
}
