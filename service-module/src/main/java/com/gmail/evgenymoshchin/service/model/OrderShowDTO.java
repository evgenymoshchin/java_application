package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderShowDTO {
    private Long id;
    private StatusEnum status;
    @ToString.Exclude
    private List<ItemForOrderDTO> items = new ArrayList<>();
    private Long itemsCount;
    private BigDecimal finalPrice;
    private String firstName;
    private String lastName;
    private String telephone;
}
