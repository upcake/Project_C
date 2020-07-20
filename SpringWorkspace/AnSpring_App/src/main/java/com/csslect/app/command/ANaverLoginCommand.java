package com.csslect.app.command;
import org.springframework.ui.Model;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.NaverMemberDTO;

public class ANaverLoginCommand implements ACommand{

	@Override
	public void execute(Model model) {		

		String email = (String)model.asMap().get("naver_email");
		String nickname = (String)model.asMap().get("naver_nickname");
		
		ANDao adao = new ANDao();
		NaverMemberDTO adto = adao.anNaverLogin(email, nickname);
		
		model.addAttribute("anNaverLogin", adto); 
		
	}
	
}
