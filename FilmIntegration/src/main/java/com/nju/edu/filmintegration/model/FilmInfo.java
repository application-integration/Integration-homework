package com.nju.edu.filmintegration.model;

/**
 * @Auther: peng
 * @Date: 2019/5/19 13:37
 * @Description:
 */
public class FilmInfo {

    private String name;
    private String cinema;
    private String date;
    private String auditorium;
    private String time;
    private String price;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public String getCinema() {
        return cinema;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
