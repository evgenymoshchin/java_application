package com.gmail.evgenymoshchin.service.constants;

public interface ExceptionConstants {
    String ORDER_NOT_FOUND_MESSAGE = "Order with id: %s was not found";
    int ONE_VALUE = 1;
    String ITEM_WAS_NOT_FOUND_MESSAGE = "Item with id: %s was not found";
    String COPY_OF = "Copy of ";
    String COMMENT_WAS_NOT_FOUND_MESSAGE = "Comment with id: %s was not found";
    String ARTICLE_WAS_NOT_FOUND_MESSAGE = "Article with id: %s was not found";
    String REVIEW_WAS_NOT_FOUND_MESSAGE = "Review with id: %s was not found";
    String VISIBILITY_CHANGE_MESSAGE = "This review id {}, wasn't contain in visibleIdsFromDB list, visibility will change";
    String VISIBILITY_WILL_CHANGE = "This review id {}, wasn't contain in selectedIds list, visibility will change";
    String USER_WAS_NOT_FOUND_MESSAGE = "User with id: %s was not found";
}
