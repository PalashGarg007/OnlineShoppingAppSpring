package com.genpact.onlineShoppingApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart")
@IdClass(CartId.class)
public class Cart {
	@Id
	@Column(name="cid")
	private Integer cid;
	
	@Id
	@Column(name="pid")
    private Integer pid;
	
    private Integer quantity;
    
    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id", updatable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id", updatable = false)
    private Product product;
    
    public Cart() {
    }

    public Cart(Integer cid, Integer pid, Integer quantity) {
        this.cid = cid;
        this.pid = pid;
        this.quantity = quantity;
    }

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{\n" +
            "\tCustomer ID = " + getCid() + "\n" +
            ",\tProduct ID = " + getPid() + "\n" +
            ",\tQuantity = " + getQuantity() + "\n" +
            "}";
    }
    
}