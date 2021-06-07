package com.master.mipasapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private  String name;
    private  String email;
    private  String password;
    private int number_login_times;
    private int number_of_sensors;

    public User(String name, String email,String password,int number_login_times, int number_of_sensors) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.number_login_times = number_login_times;
        this.number_of_sensors = number_of_sensors;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_login_times() {
        return number_login_times;
    }

    public void setNumber_login_times(int number_login_times) {
        this.number_login_times = number_login_times;
    }

    public int getNumber_of_sensors() {
        return number_of_sensors;
    }

    public void setNumber_of_sensors(int number_of_sensors) {
        this.number_of_sensors = number_of_sensors;
    }
}
