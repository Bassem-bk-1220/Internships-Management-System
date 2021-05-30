package com.example.myapplication.Modele;

public class User {
    public String fname,email,phone,password, group,specialisation,address,uploadimage;
     public String spinner;

    public User() {
    }

    public User(String fname, String email, String spinner, String phone, String password) {
        this.fname = fname;
        this.email = email;
        this.spinner=spinner;
        this.phone = phone;
        this.password = password;
    }

    public User(String fname, String email, String phone, String password, String group, String specialisation, String address, String uploadimage, String spinner) {
        this.fname = fname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.group = group;
        this.specialisation = specialisation;
        this.address = address;
        this.uploadimage = uploadimage;
        this.spinner = spinner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUploadimage() {
        return uploadimage;
    }

    public void setUploadimage(String uploadimage) {
        this.uploadimage = uploadimage;
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
