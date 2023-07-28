package com.example.coffeeapp.Class;

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

    public void changeName( String name ) { this.name = name; }
    public void changePhone( String phone ) { this.phone = phone; }
    public void changeEmail( String email ) { this.email = email; }
    public void changeAddress( String address ) { this.address = address; }

}
