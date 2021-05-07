package com.gmail.evgenymoshchin.repository.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "article_body")
    private String articleBody;
    @Column
    private String name;
    @Column(name = "summary")
    private String summary;
    @Column(name = "created_by")
    private LocalDate createdBy;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "article_id")
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}
