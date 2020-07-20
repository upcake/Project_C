package com.example.auto_medic.Dto;

import java.io.Serializable;

public class InfoDTO implements Serializable {
    String info_email, info_pw, info_nickname, info_phonenum, info_profile;

    public String getInfo_email() {
        return info_email;
    }

    public void setInfo_email(String info_email) {
        this.info_email = info_email;
    }

    public String getInfo_pw() {
        return info_pw;
    }

    public void setInfo_pw(String info_pw) {
        this.info_pw = info_pw;
    }

    public String getInfo_phonenum() {
        return info_phonenum;
    }

    public void setInfo_phonenum(String info_phonenum) {
        this.info_phonenum = info_phonenum;
    }

    public String getInfo_profile() {
        return info_profile;
    }

    public void setInfo_profile(String info_profile) {
        this.info_profile = info_profile;
    }

    public String getInfo_nickname() {
        return info_nickname;
    }

    public void setInfo_nickname(String info_nickname) {
        this.info_nickname = info_nickname;
    }

    public InfoDTO(String info_email, String info_pw, String info_nickname, String info_phonenum, String info_profile) {
        this.info_email = info_email;
        this.info_pw = info_pw;
        this.info_nickname = info_nickname;
        this.info_phonenum = info_phonenum;
        this.info_profile = info_profile;
    }

    public InfoDTO() {
    }
}
