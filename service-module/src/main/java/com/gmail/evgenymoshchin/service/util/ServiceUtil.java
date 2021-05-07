package com.gmail.evgenymoshchin.service.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceUtil {
    public static List<Integer> getNumbersOfPages(int pageSize, Long countOfObjects) {
        return IntStream.rangeClosed(1, Math.toIntExact(countOfObjects / pageSize + 1))
                .boxed()
                .collect(Collectors.toList());
    }
}
