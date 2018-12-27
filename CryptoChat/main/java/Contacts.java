package com.example.thann.cryptochat;

/**
 * Created by thann on 11/1/2017.
 */

public class Contacts {

    public String name;
    public String email;
    public String phone;
    public String key;

    public Contacts(){

    }
    public Contacts(String name,
                    String email, String phone,
                    String key)
    {
        this.name = name;
        this.email = email;
        this.key = key;
        this.phone = phone;
    }

    public String getname(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getKey(){
        return key;
    }

    public String getPhone(){
        return phone;
    }
}
