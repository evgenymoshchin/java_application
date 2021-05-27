package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ItemCountDTO {
    private Long itemId;
    @NotEmpty
    @NotNull
    @Min(1)
    @Max(100)
    private Integer itemCount;
}
