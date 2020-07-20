package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.InfoDTO;


public class InfoSelectMultiCommand implements ACommand{

	@Override
	public void execute(Model model) {			
		String info_email = (String) model.asMap().get("info_email");
		System.out.println("InfoSelectMultiCommand : " + info_email);
		ANDao adao = new ANDao();
		InfoDTO infoDTO = adao.infoSelectMulti(info_email);
		System.out.println("InfoSelectMultiCommand, infoDTO : " + infoDTO.getInfo_nickname());
		model.addAttribute("infoSelectMulti", infoDTO); 
	}
}