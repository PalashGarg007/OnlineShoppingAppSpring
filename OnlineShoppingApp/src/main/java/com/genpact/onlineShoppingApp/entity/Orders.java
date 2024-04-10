
package com.genpact.onlineShoppingApp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;
    
	@Column(name="cid")
	private Integer cid;
    
    @Column(name="pid")
    private Integer pid;
    
    private Double amount;
    private Integer quantity;
    
    @Column(name = "order_date")
    private LocalDate orderDate;
    
    @Column(name = "shiping_date")
    private LocalDate shippingDate;
    
    @Column(name="pay_id")
    private Integer payid;
    
    private boolean confirmation;
    
    //private String reviewId;
    //private String address;
    
    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id", updatable = false, insertable=false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id", updatable = false, insertable=false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "pay_id", referencedColumnName = "id", updatable = false, insertable=false)
    private Payment payment;
    
    @OneToOne(mappedBy = "orders")
    private Review review;

    public Orders() {
    }

    public Orders(Integer cid, Integer pid, Double amount,
    		Integer quantity, LocalDate orderDate, LocalDate shippingDate,
    		Integer payid, boolean confirmation) {
        this.cid = cid;
        this.pid = pid;
        this.amount = amount;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.shippingDate = shippingDate;
        this.payid = payid;
        this.confirmation = confirmation;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getShippingDate() {
        return this.shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Integer getPayId() {
        return this.payid;
    }

    public void setPayId(Integer payid) {
        this.payid = payid;
    }

    public boolean getConfirmation() {
        return this.confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
    
    public Product getProduct() {
    	return this.product;
    }
    
    public Payment getPayment() {
    	return this.payment;
    }

    @Override
    public String toString() {
        return "{\n" +
            "\tOrder ID = " + getId() + "\n" +
            "\tCustomer ID = " + getCid() + "\n" +
            "\tProduct ID = " + getPid() + "\n" +
            "\tAmount = " + getAmount() + "\n" +
            "\tQuantity = " + getQuantity() + "\n" +
            "\tOrder Date = " + getOrderDate() + "\n" +
            "\tShipping Date = " + getShippingDate() + "\n" +
            "\tPayment ID = " + getPayId() + "\n" +
            "}";
    }
    
}
