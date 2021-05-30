package com.example.myapplication.Modele;

public class Data {
    String file;
    public String iduser;
    public String idcompany;
    String idoffre;
    String country;
    String state;
    String iddata;
    String filestud;
    String filecompany;
    String fileprof;
    String filedoyen;

    public Data() {
    }

    public Data(String file, String iduser, String idcompany, String idoffre, String country, String state, String iddata) {
        this.file = file;
        this.iduser = iduser;
        this.idcompany = idcompany;
        this.idoffre = idoffre;
        this.country = country;
        this.state = state;
        this.iddata = iddata;
    }

    public Data(String file, String iduser, String idcompany, String idoffre, String country) {
        this.file = file;
        this.iduser = iduser;
        this.idcompany = idcompany;
        this.idoffre = idoffre;
        this.country=country;
    }

    public Data(String file, String iduser, String idcompany, String idoffre, String country, String state) {
        this.file = file;
        this.iduser = iduser;
        this.idcompany = idcompany;
        this.idoffre = idoffre;
        this.country = country;
        this.state = state;
    }

    public String getFileprof() {
        return fileprof;
    }

    public String getFiledoyen() {
        return filedoyen;
    }

    public void setFiledoyen(String filedoyen) {
        this.filedoyen = filedoyen;
    }

    public void setFileprof(String fileprof) {
        this.fileprof = fileprof;
    }

    public Data(String file, String iduser, String idcompany, String idoffre, String country, String state, String iddata, String filestud) {
        this.file = file;
        this.iduser = iduser;
        this.idcompany = idcompany;
        this.idoffre = idoffre;
        this.country = country;
        this.state = state;
        this.iddata = iddata;
        this.filestud = filestud;
    }

    public String getFilecompany() {
        return filecompany;
    }

    public void setFilecompany(String filecompany) {
        this.filecompany = filecompany;
    }

    public String getFilestud() {
        return filestud;
    }

    public void setFilestud(String filestud) {
        this.filestud = filestud;
    }

    public String getIddata() {
        return iddata;
    }

    public void setIddata(String iddata) {
        this.iddata = iddata;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(String idcompany) {
        this.idcompany = idcompany;
    }

    public String getIdoffre() {
        return idoffre;
    }

    public void setIdoffre(String idoffre) {
        this.idoffre = idoffre;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
