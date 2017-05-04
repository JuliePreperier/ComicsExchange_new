package com.example.comicsexchange_new;

/**
 * Created by Julie on 04.05.2017.
 */

public class User {

    public int id;
    public String username;
    public String email;
    public String password;


    public User(){
        // empty constructor
    }

    // constructor with all information
    public User(int id, String username, String email, String password){
        this.id=id;
        this.username=username;
        this.email=email;
        this.password=password;
    }

    // constructor for listview (without ids)
    public User(String username, String email, String password){
        this.email=email;
        this.password=password;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
