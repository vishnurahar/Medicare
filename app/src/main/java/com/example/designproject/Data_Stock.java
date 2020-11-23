package com.example.designproject;

public class Data_Stock {
    private String stockmedname, stockmedleft;
    public Data_Stock(String stockmedname, String stockmedleft){
        this.stockmedname=stockmedname;
        this.stockmedleft=stockmedleft;
    }

    public String getStockmedname() {
        return stockmedname;
    }
    public String getStockmedleft(){
        return  stockmedleft;
    }

}
