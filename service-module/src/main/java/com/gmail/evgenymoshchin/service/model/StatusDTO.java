package com.gmail.evgenymoshchin.service.model;

import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import lombok.Data;

@Data
public class StatusDTO {
    private StatusEnum name;
}
