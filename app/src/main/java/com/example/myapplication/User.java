package com.example.myapplication;

public class User {
    public String fname,email,phone,password;
     String spinner;

    public User() {
    }

    public User(String fname, String email, String spinner, String phone, String password) {
        this.fname = fname;
        this.email = email;
        this.spinner=spinner;
        this.phone = phone;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }
}
