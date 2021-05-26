package com.gmail.evgenymoshchin.repository.impl.constant;

public interface QueryConstants {

    String FIND_USER_WITH_ROLE_BY_USERNAME_QUERY = "select u from User u join fetch u.role where u.username = :username";
    String USERNAME_VALUE = "username";
    String FIND_ROLE_BY_NAME_QUERY = "from Role as r where r.name=:name";
    String FIND_STATUS_BY_NAME_QUERY = "from Status as s where s.name=:name";
    String NAME_VALUE = "name";
    String GET_ORDER_BY_USERNAME_QUERY = "select o from Order o join fetch o.user u where u.username = :username";
    String GET_COUNT_OF_ONE_ITEM_IN_ORDER_QUERY = "select count (i.id) from Order o join o.items i where o.id=:orderId and i.id=:itemId";
    String GET_COUNT_OF_ALL_ITEMS_IN_ORDER = "select count (o) from Order o join o.items where o.id=:id";
}
