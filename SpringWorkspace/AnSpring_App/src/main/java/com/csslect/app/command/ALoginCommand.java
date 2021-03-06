package com.csslect.app.command;
import org.springframework.ui.Model;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.MemberDTO;

public class ALoginCommand implements ACommand{

	@Override
	public void execute(Model model) {		

		String id = (String)model.asMap().get("member_email");
		String passwd = (String)model.asMap().get("member_password");
		System.out.println("커멘드" +passwd);
		
		ANDao adao = new ANDao();
		MemberDTO adto = adao.anLogin(id, passwd);
		
		model.addAttribute("anLogin", adto); 
		
	}
	
}
