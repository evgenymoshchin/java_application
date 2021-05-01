package com.gmail.evgenymoshchin.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

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
    private Boolean isShown;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public LocalDate getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(LocalDate createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getShown() {
        return isShown;
    }

    public void setShown(Boolean shown) {
        isShown = shown;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) &&
                Objects.equals(reviewBody, review.reviewBody) &&
                Objects.equals(createdBy, review.createdBy) &&
                Objects.equals(isShown, review.isShown) &&
                Objects.equals(user, review.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reviewBody, createdBy, isShown, user);
    }
}
