package com.example.se328_hesham190202;
public class users {

    private String fname;
    private String lname;
    private String mail;
    private String num;
    private String password;

    public users() {
    }

    public users(String fname, String lname, String mail, String num, String password) {
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
        this.num = num;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMail() {
        return mail;
    }

    public String getNum() {
        return num;
    }

    public String getPassword() {
        return password;
    }


}

