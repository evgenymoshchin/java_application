package com.gmail.evgenymoshchin.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "review_body")
    private String reviewBody;
    @Column(name = "created_by")
    private LocalDate createdBy;
    @Column(name = "is_shown")
    private Boolean isVisible;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
