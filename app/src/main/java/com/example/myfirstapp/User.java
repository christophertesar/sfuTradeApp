package com.example.myfirstapp;

public class User {
    public String name, email;

    public User(String email) {
        this.email = email;
    }

    //empty Constructor
//    public User() {}

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }
}
