package com.csslect.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.EmerDTO;


public class EmerSelectMultiCommand implements ACommand{

	@Override
	public void execute(Model model) { 		
		ANDao adao = new ANDao();
		System.out.println("EmerSelectCommand ");
		ArrayList<EmerDTO> adtos = adao.EmerSelectMulti(); 
		
		model.addAttribute("EmerSelectMulti", adtos); //jsp에서 사용하려고 보내기
		
	}
	
}
