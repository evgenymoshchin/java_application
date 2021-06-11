package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ItemShowDTO {
    private String name;
    private Long orderId;
    private StatusEnum statusEnum;
    private Long count;
    private BigDecimal finalPrice;
    private LocalDate date;
}
