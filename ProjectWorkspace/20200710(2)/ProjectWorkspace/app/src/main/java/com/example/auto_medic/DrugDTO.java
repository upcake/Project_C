package com.example.auto_medic;

public class DrugDTO {
    private String name,addr,tel;
    private int dutytime1s,dutytime1c;  //월
    private int dutytime2s,dutytime2c;
    private int dutytime3s,dutytime3c;
    private int dutytime4s,dutytime4c;
    private int dutytime5s,dutytime5c;
    private int dutytime6s,dutytime6c;  //금
    private int dutytime7s,dutytime7c;  //토
    private int dutytime8s,dutytime8c;  //일
    double lat,lon;         //경도,위도

    public DrugDTO(String name, String addr, String tel, double lat, double lon) {
        this.name = name;
        this.addr = addr;
        this.tel = tel;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getDutytime1s() {
        return dutytime1s;
    }

    public void setDutytime1s(int dutytime1s) {
        this.dutytime1s = dutytime1s;
    }

    public int getDutytime1c() {
        return dutytime1c;
    }

    public void setDutytime1c(int dutytime1c) {
        this.dutytime1c = dutytime1c;
    }

    public int getDutytime2s() {
        return dutytime2s;
    }

    public void setDutytime2s(int dutytime2s) {
        this.dutytime2s = dutytime2s;
    }

    public int getDutytime2c() {
        return dutytime2c;
    }

    public void setDutytime2c(int dutytime2c) {
        this.dutytime2c = dutytime2c;
    }

    public int getDutytime3s() {
        return dutytime3s;
    }

    public void setDutytime3s(int dutytime3s) {
        this.dutytime3s = dutytime3s;
    }

    public int getDutytime3c() {
        return dutytime3c;
    }

    public void setDutytime3c(int dutytime3c) {
        this.dutytime3c = dutytime3c;
    }

    public int getDutytime4s() {
        return dutytime4s;
    }

    public void setDutytime4s(int dutytime4s) {
        this.dutytime4s = dutytime4s;
    }

    public int getDutytime4c() {
        return dutytime4c;
    }

    public void setDutytime4c(int dutytime4c) {
        this.dutytime4c = dutytime4c;
    }

    public int getDutytime5s() {
        return dutytime5s;
    }

    public void setDutytime5s(int dutytime5s) {
        this.dutytime5s = dutytime5s;
    }

    public int getDutytime5c() {
        return dutytime5c;
    }

    public void setDutytime5c(int dutytime5c) {
        this.dutytime5c = dutytime5c;
    }

    public int getDutytime6s() {
        return dutytime6s;
    }

    public void setDutytime6s(int dutytime6s) {
        this.dutytime6s = dutytime6s;
    }

    public int getDutytime6c() {
        return dutytime6c;
    }

    public void setDutytime6c(int dutytime6c) {
        this.dutytime6c = dutytime6c;
    }

    public int getDutytime7s() {
        return dutytime7s;
    }

    public void setDutytime7s(int dutytime7s) {
        this.dutytime7s = dutytime7s;
    }

    public int getDutytime7c() {
        return dutytime7c;
    }

    public void setDutytime7c(int dutytime7c) {
        this.dutytime7c = dutytime7c;
    }

    public int getDutytime8s() {
        return dutytime8s;
    }

    public void setDutytime8s(int dutytime8s) {
        this.dutytime8s = dutytime8s;
    }

    public int getDutytime8c() {
        return dutytime8c;
    }

    public void setDutytime8c(int dutytime8c) {
        this.dutytime8c = dutytime8c;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
