package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ItemDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String name;
    private UUID uuid;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String description;
}
