package com.genpact.onlineShoppingApp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	@Column(unique = true)
	private String method;
    private Double discount;
    
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Orders> orderList;
    
    public Payment() {
    }

    public Payment(String method, Double discount) {
        this.method = method;
        this.discount = discount;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "{\n" +
            "\tPayment ID = " + getId() + "\n" +
            "\tMethod = " + getMethod() + "\n" +
            "\tDiscount = " + getDiscount() + "\n" +
            "}";
    }

}
