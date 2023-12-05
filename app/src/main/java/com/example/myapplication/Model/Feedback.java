package com.example.myapplication.Model;

public class Feedback {
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String username;
    public String email;
    public String time;
    public String overall;
    public String degree;
    public String wind;
    public String other;
    public Feedback(String username, String email, String time,
                    String overall, String degree, String wind, String other){
        this.username=username;
        this.email=email;
        this.time=time;
        this.overall=overall;
        this.degree=degree;
        this.wind=wind;
        this.other=other;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
