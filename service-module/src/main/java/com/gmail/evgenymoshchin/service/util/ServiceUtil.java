package com.gmail.evgenymoshchin.service.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.FIRST_PAGE_VALUE;

public class ServiceUtil {

    public static List<Integer> getNumbersOfPages(int pageSize, Long countOfObjects) {
        return IntStream.rangeClosed(FIRST_PAGE_VALUE, Math.toIntExact(countOfObjects / pageSize + FIRST_PAGE_VALUE))
                .boxed()
                .collect(Collectors.toList());
    }
}
