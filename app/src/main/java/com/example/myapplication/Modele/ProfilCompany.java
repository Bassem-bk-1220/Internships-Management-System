package com.example.myapplication.Modele;

public class ProfilCompany {
String nameCompany,address,field,description,profileimage, email_company;

    public ProfilCompany() {
    }

    public ProfilCompany(String nameCompany, String address, String field, String description, String profileimage,String email_company) {
        this.nameCompany = nameCompany;
        this.address = address;
        this.field = field;
        this.description = description;
        this.profileimage = profileimage;
        this.email_company=email_company;
    }

    public String getEmail_company() {
        return email_company;
    }

    public void setEmail_company(String email_company) {
        this.email_company = email_company;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public String getAddress() {
        return address;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
}
