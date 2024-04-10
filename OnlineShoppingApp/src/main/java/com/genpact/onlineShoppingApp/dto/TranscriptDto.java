package com.genpact.onlineShoppingApp.dto;

import java.time.LocalDate;

public class TranscriptDto {
	private Integer id;
	private Integer quantity;
	private Double totalAmount;
	private LocalDate orderDate;
	private String productName;
	private Double amountPerProduct;
	private Double discount;
	
	public TranscriptDto() {
	}
	
	public TranscriptDto(Integer id, Integer quantity, Double totalAmount, LocalDate orderDate, String productName,
			Double amountPerProduct, Double discount) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.orderDate = orderDate;
		this.productName = productName;
		this.amountPerProduct = amountPerProduct;
		this.discount = discount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getAmountPerProduct() {
		return amountPerProduct;
	}
	public void setAmountPerProduct(Double amountPerProduct) {
		this.amountPerProduct = amountPerProduct;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
