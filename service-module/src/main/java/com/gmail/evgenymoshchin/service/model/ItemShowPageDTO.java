package com.gmail.evgenymoshchin.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ItemShowPageDTO {
    private List<ItemShowDTO> items = new ArrayList<>();
    private Long pagesCount;
    private List<Integer> numbersOfPages = new ArrayList<>();
}
