package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemForOrderDTO {
    private String name;
    private Long count;
    private BigDecimal price;
}
