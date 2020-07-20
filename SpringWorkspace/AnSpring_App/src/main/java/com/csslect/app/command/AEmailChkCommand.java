package com.csslect.app.command;
import org.springframework.ui.Model;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.MemberDTO;

public class AEmailChkCommand implements ACommand{

	@Override
	public void execute(Model model) {		
		String member_email = (String)model.asMap().get("member_email");
		
		ANDao adao = new ANDao();
		MemberDTO adto = adao.anEmailChk(member_email);
		
		model.addAttribute("anEmailChk", adto); 
				
	}
	
}
