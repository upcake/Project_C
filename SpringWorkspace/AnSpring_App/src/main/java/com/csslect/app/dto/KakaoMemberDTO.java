package com.csslect.app.dto;

public class KakaoMemberDTO {
	private String kakao_email;
	private String kakao_nickname;
	
	//데이터베이스에 멤버정보를 추가할때
	public KakaoMemberDTO(String kakao_email, String kakao_nickname) {
		super();
		this.kakao_email = kakao_email;
		this.kakao_nickname = kakao_nickname;
	}
	

	//Getters & Setters
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
