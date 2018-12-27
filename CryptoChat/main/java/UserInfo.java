package com.example.thann.cryptochat;

/**
 * Created by thann on 10/24/2017.
 */

public class UserInfo {

    public String name;
    public String username;
    public String email;
    public String PublicKey;

    public UserInfo(String name, String username,
                    String email, String PublicKey)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.PublicKey = PublicKey;
    }

    public String getUName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getUEmail(){
        return email;
    }

    public String getUPublicKey(){
        return PublicKey;
    }
}
