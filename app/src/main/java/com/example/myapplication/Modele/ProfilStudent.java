package com.example.myapplication.Modele;

public class ProfilStudent {
    public String group,specialisation,uploadfile,uploadimage,bio,address,idStud;

    public ProfilStudent() {
    }

    public ProfilStudent(String uploadimage, String group, String specialisation,String uploadfile, String bio, String address, String idStud) {
        this.group = group;
        this.specialisation = specialisation;
        this.uploadfile=uploadfile;
        this.uploadimage=uploadimage;
        this.bio=bio;
        this.address=address;
        this.idStud=idStud;
    }

    public String getIdStud() {
        return idStud;
    }

    public void setIdStud(String idStud) {
        this.idStud = idStud;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUploadfile() {
        return uploadfile;
    }

    public String getUploadimage() {
        return uploadimage;
    }

    public void setUploadfile(String uploadfile) {
        this.uploadfile = uploadfile;
    }

    public void setUploadimage(String uploadimage) {
        this.uploadimage = uploadimage;
    }

    public String getGroup() {
        return group;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
}
