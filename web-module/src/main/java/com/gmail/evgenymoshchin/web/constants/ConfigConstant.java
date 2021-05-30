package com.gmail.evgenymoshchin.web.constants;

public interface ConfigConstant {
    String ADMIN_URL = "/users/get";
    String SALE_USER_URL = "/articles/get";
    String ACCESS_DENIED_URL = "/access-denied";
    String CUSTOMER_USER_URL = "/articles/get";
    String SECURE_API_USER_URL = "/access-denied";
    String LOGIN_PAGE_URL = "/login";
    String USERS_CONTROLLER_MAPPING_URL = "/users/**";
    String DEFAULT_URL = "/";
    String API_URL_VALUE = "/api/**";
    String PROFILES_URL = "/profiles/**";
    String ARTICLES_URL = "/articles";
    String DELETE_ARTICLE_URL = "/delete-article-by-id";
    String ADD_URL = "/add";
    String ITEMS_URL = "/items";
    String GET_URL = "/get";
    String SHOW_ARTICLE_URL = "/show-article-by-id";
    String UPDATE_ARTICLE_URL = "/update";
    String ORDERS_URL = "/orders";
    String GET_ORDERS_URL = "/show";
    String UPDATE_ORDER_STATUS_URL = "/update-status";
    String SHOW_ORDER_BY_ID_URL = "/show-order-by-id";
    String REVIEWS_URL = "/reviews";
    String SHOW_ITEM_URL = "/show-item-by-id";
    String DELETE_ITEM_URL = "/delete-item-by-id";
    String COPY_ITEM_URL = "/copy-item";
    String ADD_ITEM_TO_ORDER_URL = "/add-item-to-order";
    String REMOVE_REVIEW_URL = "/remove/{id}";
    String CHANGE_REVIEW_URL = "/change";
}
