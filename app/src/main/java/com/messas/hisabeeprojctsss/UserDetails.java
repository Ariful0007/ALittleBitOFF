package com.messas.hisabeeprojctsss;

public class UserDetails {
    String name,email,phone,uuid,bookname,authername,date,time;

    public UserDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthername() {
        return authername;
    }

    public void setAuthername(String authername) {
        this.authername = authername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserDetails(String name, String email, String phone, String uuid, String bookname, String authername, String date, String time) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.uuid = uuid;
        this.bookname = bookname;
        this.authername = authername;
        this.date = date;
        this.time = time;
    }
}
