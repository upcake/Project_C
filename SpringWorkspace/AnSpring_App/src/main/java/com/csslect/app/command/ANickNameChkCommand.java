package com.csslect.app.command;
import org.springframework.ui.Model;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.MemberDTO;

public class ANickNameChkCommand implements ACommand{

	@Override
	public void execute(Model model) {		
		String member_nickname = (String)model.asMap().get("member_nickname");
		
		ANDao adao = new ANDao();
		MemberDTO adto = adao.anNickNameChk(member_nickname);
		
		model.addAttribute("anNickNameChk", adto); 
				
	}
	
}
