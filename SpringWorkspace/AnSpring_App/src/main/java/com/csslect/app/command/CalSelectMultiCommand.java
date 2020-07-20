package com.csslect.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.EmerDTO;
import com.csslect.app.dto.RecMedDTO;


public class CalSelectMultiCommand implements ACommand{

	@Override
	public void execute(Model model) { 		
		ANDao adao = new ANDao();
		System.out.println("CalSelectCommand ");
		ArrayList<RecMedDTO> adtos = adao.CalSelectMulti(); 
		
		model.addAttribute("CalSelectMulti", adtos); //jsp에서 사용하려고 보내기
		
	}
	
}
