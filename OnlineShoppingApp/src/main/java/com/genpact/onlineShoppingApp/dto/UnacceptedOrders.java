package com.genpact.onlineShoppingApp.dto;

import java.time.LocalDate;

public class UnacceptedOrders {
	private String productName;
	private String brand;
	private String category;
	private Double cost;
	private Integer id; //order id
	private Integer quantity;
	private LocalDate order_date;
	private Boolean confirmation;
	private String customerName;
	private String contact;
	private String address;
	private Double amount;
	
	public UnacceptedOrders() {
	
	}

	public UnacceptedOrders(String productName, String brand, String category, Double cost, Integer id,
			Integer quantity, LocalDate order_date, Boolean confirmation, String customerName, String contact,
			String address, Double amount) {
		super();
		this.productName = productName;
		this.brand = brand;
		this.category = category;
		this.cost = cost;
		this.id = id;
		this.quantity = quantity;
		this.order_date = order_date;
		this.confirmation = confirmation;
		this.customerName = customerName;
		this.contact = contact;
		this.address = address;
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
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

	public LocalDate getOrder_date() {
		return order_date;
	}

	public void setOrder_date(LocalDate order_date) {
		this.order_date = order_date;
	}

	public Boolean getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Boolean confirmation) {
		this.confirmation = confirmation;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
