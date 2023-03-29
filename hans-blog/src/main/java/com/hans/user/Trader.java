package com.hans.user;

import lombok.Data;

@Data
public class Trader {
    private String name;
    private String city;


    public Trader() {
    }
    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }
}
