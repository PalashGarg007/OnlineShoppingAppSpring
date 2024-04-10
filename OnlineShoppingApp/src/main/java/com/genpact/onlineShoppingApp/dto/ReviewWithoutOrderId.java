package com.genpact.onlineShoppingApp.dto;

public class ReviewWithoutOrderId {
	private String review;
	private Double rating;
	
	public ReviewWithoutOrderId() {
		
	}
	
	public ReviewWithoutOrderId(String review, Double rating) {
		super();
		this.review = review;
		this.rating = rating;
	}
	
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}
