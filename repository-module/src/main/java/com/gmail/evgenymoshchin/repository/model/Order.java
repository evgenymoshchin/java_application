package com.gmail.evgenymoshchin.repository.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "created_by")
    private LocalDate createdBy;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE,
            optional = false
    )
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE,
            optional = false
    )
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_item",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    @ToString.Exclude
    private Set<Item> items = new HashSet<>();
}
