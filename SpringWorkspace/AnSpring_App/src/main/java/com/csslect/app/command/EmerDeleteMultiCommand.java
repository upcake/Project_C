package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;


public class EmerDeleteMultiCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		String emergency_name = 
		(String)model.asMap().get("emergency_name");
		
		System.out.println("EmerDeleteMultiCommand()");
		System.out.println("Emer "+emergency_name);
		ANDao adao = new ANDao();
		adao.emerDeleteMulti(emergency_name);		
			
	}	 

}
