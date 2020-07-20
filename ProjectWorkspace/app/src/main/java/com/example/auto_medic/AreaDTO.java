package com.example.auto_medic;

public class AreaDTO {
    private String name;
    private Double addr;    //경도
    private Double phone;   //위도

    public AreaDTO(){}

    public AreaDTO(String name, Double addr, Double phone) {
        this.name = name;
        this.addr = addr;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAddr() {
        return addr;
    }

    public void setAddr(Double addr) {
        this.addr = addr;
    }

    public Double getPhone() {
        return phone;
    }

    public void setPhone(Double phone) {
        this.phone = phone;
    }
}
