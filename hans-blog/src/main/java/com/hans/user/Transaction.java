package com.hans.user;

import lombok.Data;

@Data
public class Transaction {
    private Trader trader;
    private Integer year;
    private Integer value;


    public Transaction() {
    }
    public Transaction(Trader trader, Integer year, Integer value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }
}
