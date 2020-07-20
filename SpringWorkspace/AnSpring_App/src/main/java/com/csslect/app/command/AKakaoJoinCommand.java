package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class AKakaoJoinCommand implements ACommand{
	
	@Override
	public void execute(Model model) {		
		String kakao_email = (String)model.asMap().get("kakao_email");
		String kakao_nickname = (String)model.asMap().get("kakao_nickname");
		
		ANDao adao = new ANDao();
		int state  = adao.anKakaoJoin(kakao_email, kakao_nickname);
		
		model.addAttribute("anKakaoJoin", String.valueOf(state)); 
				
	}


}
