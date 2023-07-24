package com.example.coffeeapp;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String phone;
    private String email;
    private String address;



    public User( String name, String phone, String email, String address ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

}
