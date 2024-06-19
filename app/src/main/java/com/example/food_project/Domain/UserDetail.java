package com.example.food_project.Domain;

import android.widget.EditText;

public class UserDetail {
    public String nameEdt;
    public String emailEdt;
    public String passEdt;

    public UserDetail() {

    }

    public UserDetail(String nameEdt, String emailEdt, String passEdt) {
        this.nameEdt = nameEdt;
        this.emailEdt = emailEdt;
        this.passEdt = passEdt;
    }

    public String getNameEdt() {
        return nameEdt;
    }

    public String getEmailEdt() {
        return emailEdt;
    }

    public String getPassEdt() {
        return passEdt;
    }
}
