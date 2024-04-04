package com.genpact.onlineShoppingApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="favorite")
@IdClass(FavoriteId.class)
public class Favorite {
	@Id
	@Column(name="cid")
	private Integer cid;
	
	@Id
	@Column(name="pid")
	private Integer pid;
	
	@ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "id", updatable = false)
    private Customer customer;
	
	@ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id", updatable = false)
    private Product product;
	
    public Favorite() {
    }

    public Favorite(Integer cid, Integer pid) {
        this.cid = cid;
        this.pid = pid;
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

    @Override
    public String toString() {
        return "{\n" +
            "\tCustomer ID = " + getCid() + "\n" +
            "\tProduct ID = " + getPid() + "\n" +
            "}";
    }

}