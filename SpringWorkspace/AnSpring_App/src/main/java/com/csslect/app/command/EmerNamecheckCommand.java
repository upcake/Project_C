package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;


public class EmerNamecheckCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		String emergency_name = 
		(String)model.asMap().get("emergency_name");
		String emergency_phonenum = 
				(String)model.asMap().get("emergency_phonenum");
		
		
		
		
		System.out.println("EmerNamecheckCommand()");
		System.out.println("Emer "+emergency_name);
		System.out.println("Emer "+emergency_phonenum);
		ANDao adao = new ANDao();
		int state=adao.emerNamechk(emergency_name,emergency_phonenum);
		
		System.out.println(String.valueOf(state)+"Command");
		
		model.addAttribute("emerNamecheck", String.valueOf(state));
			
	}	 

}
