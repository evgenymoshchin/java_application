package com.gmail.evgenymoshchin.repository.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column
    private String name;
    @Column(name = "unique_number", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @Column
    private BigDecimal price;
    @Column
    private String description;
}
