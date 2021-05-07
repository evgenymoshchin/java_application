package com.gmail.evgenymoshchin.repository.impl.constant;

public interface QueryConstants {

    String FIND_ITEM_BY_NAME_QUERY = "from Item as i where i.name=:name";
    String FIND_ITEM_BY_PRICE_QUERY = "from Item as i join fetch i.itemDetails id where id.price=:price";
    String FIND_ITEM_BY_NAME_AND_PRICE_BETWEEN = "from Item as i join fetch i.itemDetails id where i.name=:name and id.price between :minPrice and :maxPrice";
    String FIND_USER_WITH_ROLE_BY_USERNAME_QUERY = "select u from User u join fetch u.role where u.username = :username";
    String USERNAME_VALUE = "username";
}
