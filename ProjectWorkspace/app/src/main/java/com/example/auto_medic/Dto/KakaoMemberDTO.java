package com.example.auto_medic.Dto;

public class KakaoMemberDTO {
    String kakao_email;
    String kakao_nickname;

    public KakaoMemberDTO(String kakao_email, String kakao_nickname) {
        this.kakao_email = kakao_email;
        this.kakao_nickname = kakao_nickname;
    }

    public String getKakao_email() {
        return kakao_email;
    }

    public void setKakao_email(String kakao_email) {
        this.kakao_email = kakao_email;
    }

    public String getKakao_nickname() {
        return kakao_nickname;
    }

    public void setKakao_nickname(String kakao_nickname) {
        this.kakao_nickname = kakao_nickname;
    }
}
