package com.genpact.onlineShoppingApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="review")
public class Review {
	@Id
	@Column(name="id")
	private Integer id;
	
	private Double rating;
	private String review;

    public Review() {
    }
    
    @OneToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id", unique = true, nullable = false, updatable = false)
    private Orders orders;

    public Review(Integer id, Double rating, String review) {
        this.id = id;
        this.rating = rating;
        this.review = review;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "{\n" +
            "\tOrder ID = " + getId() + "\n" +
            "\tRating = " + getRating() + "\n" +
            "\tReview = " + getReview() + "\n" +
            "}";
    }

}
