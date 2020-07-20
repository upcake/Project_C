package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class EmerupdateMultiNoCommand2 implements ACommand{	
	
	@Override
	public void execute(Model model) {
		
		String emergency_name=(String)model.asMap().get("emergency_name");		
		int alarm= Integer.parseInt((String) model.asMap().get("alarm"));
		int alarmperiod= Integer.parseInt((String) model.asMap().get("alarmperiod"));
		int message=Integer.parseInt((String) model.asMap().get("message"));
		int tel= Integer.parseInt((String) model.asMap().get("tel"));
		
		System.out.println("updateCommand2");
		System.out.println(emergency_name);		
		System.out.println(alarm);
		System.out.println(alarmperiod);
		System.out.println(message);
		System.out.println(tel);
		
		ANDao adao = new ANDao();
		
		adao.emerUpdateMultiNo2(emergency_name,alarm,alarmperiod,message,tel);
		
		
	}	 

}
