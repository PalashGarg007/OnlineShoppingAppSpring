package com.genpact.onlineShoppingApp.entity;

import java.io.Serializable;
import java.util.Objects;

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

@SuppressWarnings("serial")
class CartId implements Serializable{
	private Integer pid;
	private Integer cid;
	
	public CartId() {
		
	}
	
	CartId(Integer pid, Integer cid) {
		super();
		this.pid = pid;
		this.cid = cid;
	}
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cid, pid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartId other = (CartId) obj;
		return Objects.equals(cid, other.cid) && Objects.equals(pid, other.pid);
	}
	
}
