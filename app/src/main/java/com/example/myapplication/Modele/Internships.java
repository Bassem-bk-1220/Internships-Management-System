package com.example.myapplication.Modele;

public class Internships {
    String keyword, period,name_company,description_intern,salary,payment,id_company,idinternship;

    public Internships() {
    }

    public String getIdinternship() {
        return idinternship;
    }

    public void setIdinternship(String idinternship) {
        this.idinternship = idinternship;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getPeriod() {
        return period;
    }

    public String getName_company() {
        return name_company;
    }

    public String getId_company() {
        return id_company;
    }

    public void setId_company(String id_company) {
        this.id_company = id_company;
    }

    public String getDescription_intern() {
        return description_intern;
    }

    public String getSalary() {
        return salary;
    }

    public String getPayment() {
        return payment;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setName_company(String name_company) {
        this.name_company = name_company;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDescription_intern(String description_intern) {
        this.description_intern = description_intern;
    }


    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
