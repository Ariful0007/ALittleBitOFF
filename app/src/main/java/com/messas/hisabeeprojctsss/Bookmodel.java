package com.messas.hisabeeprojctsss;

public class Bookmodel {
    String bookname,bookimage,authorname,category,uuid,time,email,useruid,flag;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookimage() {
        return bookimage;
    }

    public void setBookimage(String bookimage) {
        this.bookimage = bookimage;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Bookmodel(String bookname, String bookimage, String authorname, String category,
                     String uuid, String time, String email, String useruid, String flag) {
        this.bookname = bookname;
        this.bookimage = bookimage;
        this.authorname = authorname;
        this.category = category;
        this.uuid = uuid;
        this.time = time;
        this.email = email;
        this.useruid = useruid;
        this.flag = flag;
    }

    public Bookmodel() {
    }
}
