package com.example.designproject;

public class NavItem {
    private String mText1, mText2;

    public NavItem(String text1, String text2){
        mText1=text1;   // Medicine Name
        mText2=text2;    //  TIME
    }
    public void changeText1(String text) {
        mText1 = text;
    }
    public void changeText2(String text) {
        mText2 = text;
    }
    public String getText1(){
        return mText1;
    }
    public String getText2(){
        return mText2;
    }

}
