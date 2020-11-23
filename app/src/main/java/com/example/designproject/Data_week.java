package com.example.designproject;

public class Data_week {

    private String med_name,repdate,reptime,swallow;
    public Data_week(String med_name, String repdate, String reptime,String swallow){
        this.med_name=med_name;
        this.repdate=repdate;
        this.reptime=reptime;
        this.swallow=swallow;
    }
    public String getMed_name() {
        return med_name;
    }

    public String getRepdate() {
        return repdate;
    }
    public String getReptime(){
        return reptime;
    }
    public String getSwallow(){return swallow;}
}
