package com.csslect.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.AlarmDTO;

public class AlarmSelectMultiCommand implements ACommand {

	@Override
	public void execute(Model model) {			
		ANDao adao = new ANDao();
		ArrayList<AlarmDTO> dtoArrayList = adao.alarmSelectMulti();
		
		model.addAttribute("alarmSelectMulti", dtoArrayList); 
		
	}
	
}
