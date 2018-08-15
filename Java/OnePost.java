package com.connect.ansh0.connectmany;

public class OnePost {
    String name;
    String text;
    String dpUrl;
    String picUrl;
    String date;
    String time;
    String id;
    String emailAdd;

    public OnePost() {

    }

    public OnePost(String name, String text, String dpUrl, String picUrl, String date, String time, String id, String emailAdd) {
        this.name = name;
        this.text = text;
        this.dpUrl = dpUrl;
        this.picUrl = picUrl;
        this.date = date;
        this.time = time;
        this.id = id;
        this.emailAdd = emailAdd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDpUrl() {
        return dpUrl;
    }

    public void setDpUrl(String dpUrl) {
        this.dpUrl = dpUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }
}
