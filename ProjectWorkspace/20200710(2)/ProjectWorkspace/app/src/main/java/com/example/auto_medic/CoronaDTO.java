package com.example.auto_medic;

public class CoronaDTO {
    private String name;
    private Double addr;    //경도(long)
    private Double phone;   //위도(lat)
    private String rearaddr;    //진짜 주소
    private String realtel;     //진짜 번호

    public CoronaDTO(String name, Double addr, Double phone,String readaddr,String realtel) {
        this.name = name;
        this.addr = addr;
        this.phone = phone;
        this.rearaddr = readaddr;
        this.realtel = realtel;

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

    public String getRearaddr() {
        return rearaddr;
    }

    public void setRearaddr(String rearaddr) {
        this.rearaddr = rearaddr;
    }

    public String getRealtel() {
        return realtel;
    }

    public void setRealtel(String realtel) {
        this.realtel = realtel;
    }
}