package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class anPwChkCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		String member_email = (String)model.asMap().get("member_email");
		String member_password = (String)model.asMap().get("member_password");		
		
		ANDao adao = new ANDao();
		
		adao.anPwChk(member_email, member_password);
		
		
	}	 

}
