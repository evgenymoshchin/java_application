package com.gmail.evgenymoshchin.repository.impl.constant;

public interface QueryConstants {

    String FIND_USER_WITH_ROLE_BY_USERNAME_QUERY = "select u from User u join fetch u.role where u.username = :username";
    String USERNAME_VALUE = "username";
    String FIND_ROLE_BY_NAME_QUERY = "from Role as r where r.name=:name";
    String ROLE_NAME_VALUE = "name";
}
