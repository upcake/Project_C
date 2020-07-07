package com.example.auto_medic.Dto;

public class NaverMemberDTO {
    String naver_email;
    String naver_nickname;

    public NaverMemberDTO(String naver_email, String naver_nickname) {
        this.naver_email = naver_email;
        this.naver_nickname = naver_nickname;
    }

    public String getNaver_email() {
        return naver_email;
    }

    public void setNaver_email(String naver_email) {
        this.naver_email = naver_email;
    }

    public String getNaver_nickname() {
        return naver_nickname;
    }

    public void setNaver_nickname(String naver_nickname) {
        this.naver_nickname = naver_nickname;
    }
}
