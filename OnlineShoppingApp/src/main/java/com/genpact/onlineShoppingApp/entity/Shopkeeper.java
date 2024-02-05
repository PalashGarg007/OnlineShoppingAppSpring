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
@Table(name = "shopkeeper")
public class Shopkeeper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;
    
	private String name;
    private String contact;
    private String email;
    
    @Column(name = "userName", unique = true)
    private String userName;
    
    @Column(name = "_password")
    private String password;
    
    @OneToMany(mappedBy = "shopkeeper", cascade = CascadeType.ALL)
    private List<Product> products;
    
    public Shopkeeper() {
    }

    public Shopkeeper(String name, String contact, String email,
    		String userName, String password) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\n" +
            "\tShopkeeper ID = " + getId() + "\n" +
            "\tName = " + getName() + "\n" +
            "\tContact = " + getContact() + "\n" +
            "\tEmail = " + getEmail() + "\n" +
            "\tUser Name = " + getUserName() + "\n" +
            "\tPassword = " + getPassword() + "\n" +
            "}";
    }

}
