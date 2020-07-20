package com.example.auto_medic.Dto;

public class MemberDTO {
    String member_email;
    String member_password;
    String member_nickname;
    String member_phonenum;

    // 데이터베이스에 멤버정보를 추가할때
    public MemberDTO(String member_email, String member_password, String member_nickname, String member_phonenum) {
        this.member_email = member_email;
        this.member_password = member_password;
        this.member_nickname = member_nickname;
        this.member_phonenum = member_phonenum;
    }

    // Getters & Setters
    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_phonenum() {
        return member_phonenum;
    }

    public void setMember_phonenum(String member_phonenum) {
        this.member_phonenum = member_phonenum;
    }
}
