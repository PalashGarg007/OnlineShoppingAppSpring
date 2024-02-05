package com.genpact.onlineShoppingApp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;
	
	@Column(name="sid")
    private Integer sid;
	
    private String name;
    private String brand;
    private String category;
    private Double cost;
    private Integer warehouse;
    private Double rating;
    private Integer purchased;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Orders> orders;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Cart> carts;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Favorite> favorites;
    
    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "id", updatable = false, insertable=false)
    private Shopkeeper shopkeeper;
    
    public Product() {
    }

    public Product(Integer sid, String name, String brand, String category, Double cost,
    		Integer warehouse, Double rating, Integer purchased) {
        this.sid = sid;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.cost = cost;
        this.warehouse = warehouse;
        this.rating = rating;
        this.purchased = purchased;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return this.sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getBrand() {
		return this.brand;
	}
    
    public void setBrand(String brand) {
		this.brand = brand;
	}

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Integer warehouse) {
        this.warehouse = warehouse;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getPurchased() {
        return this.purchased;
    }

    public void setPurchased(Integer purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        return "{\n" +
            "Product ID = " + getId() + "\n" +
            "Shopkeeper ID = " + getSid() + "\n" +
            "Name = " + getName() + "\n" +
            "Category = " + getCategory() + "\n" +
            "Cost = " + getCost() + "\n" +
            "Warehouse = " + getWarehouse() + "\n" +
            "Rating = " + getRating() + "\n" +
            "Purchased = " + getPurchased() + "\n" +
            "}";
    }

}
