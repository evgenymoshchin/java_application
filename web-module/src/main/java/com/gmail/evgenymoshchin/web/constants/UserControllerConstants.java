package com.gmail.evgenymoshchin.web.constants;

public interface UserControllerConstants {
    String EXIST_USER_MESSAGE_VALUE = "An user for that email is already exists.";
    String USER_CONTROLLER_MAPPING_VALUE = "/users";
    String ADD_USERS_MAPPING_VALUE = "/add";
    String GET_USERS_MAPPING_VALUE = "/get";
    String USERS_ATTRIBUTE_VALUE = "users";
    String USERS_GET_REDIRECTION_PATH_VALUE = "redirect:/users/get";
    String ADD_USER_VIEW_NAME_VALUE = "add_user";
    String UPDATE_USER_VIEW_NAME_VALUE = "update_user";
    String GET_USERS_VIEW_NAME_VALUE = "get_all_users";
    String UPDATE_USER_PASSWORD_URL_VALUE = "/update-password/{id}";
    String UPDATE_USER_ROLE_MAPPING_VALUE = "/update-role/{id}";
    String REMOVE_USER_MAPPING_VALUE = "/remove";
    String ROLES_ATTRIBUTE_VALUE = "roles";
    String ROLE_ATTRIBUTE_VALUE = "role";
    String USER_ATTRIBUTE_VALUE = "user";
    String ERROR_ATTRIBUTE_VALUE = "error";
    String SELECTED_USERS_PARAMETER_VALUE = "selectedUsers";
}
