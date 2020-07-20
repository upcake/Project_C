package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.KakaoMemberDTO;

public class AKakaoLoginCommand implements ACommand{

	@Override
	public void execute(Model model) {		

		String email = (String)model.asMap().get("kakao_email");
		String nickname = (String)model.asMap().get("kakao_nickname");
		
		ANDao adao = new ANDao();
		KakaoMemberDTO adto = adao.anKakaoLogin(email, nickname);
		
		model.addAttribute("anKakaoLogin", adto); 
		
	}
}
