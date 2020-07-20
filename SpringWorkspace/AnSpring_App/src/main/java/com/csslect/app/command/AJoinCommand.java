package com.csslect.app.command;
import org.springframework.ui.Model;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.MemberDTO;

public class AJoinCommand implements ACommand{

	@Override
	public void execute(Model model) {		
		String member_email = (String)model.asMap().get("member_email");
		String member_password = (String)model.asMap().get("member_password");	
		String member_nickname = (String)model.asMap().get("member_nickname");
		String member_phonenum = (String)model.asMap().get("member_phonenum");
		
		ANDao adao = new ANDao();
		int state = adao.anJoin(member_email, member_password, member_nickname, member_phonenum);
		
		model.addAttribute("anJoin", String.valueOf(state)); 
		
	}
	
}
