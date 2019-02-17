package com.example.mypc.model;

public class Music {
    private String ma;
    private String ten;
    private String casi;
    private boolean thich;

    public Music() {
    }

    public Music(String ma, String ten, String casi, boolean thich) {
        this.ma = ma;
        this.ten = ten;
        this.casi = casi;
        this.thich = thich;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCasi() {
        return casi;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }

    public boolean isThich() {
        return thich;
    }

    public void setThich(boolean thich) {
        this.thich = thich;
    }
}
