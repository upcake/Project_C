package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;


public class EmerJoinCommand implements ACommand{

	@Override
	public void execute(Model model) {
		String emergency_name=(String)model.asMap().get("emergency_name");
		String emergency_phonenum=(String)model.asMap().get("emergency_phonenum");
		String emergency_email=((String) model.asMap().get("emergency_email"));		
		int alarm= Integer.parseInt((String) model.asMap().get("alarm"));
		int alarmperiod= Integer.parseInt((String) model.asMap().get("alarmperiod"));
		int message=Integer.parseInt((String) model.asMap().get("message"));
		int tel= Integer.parseInt((String) model.asMap().get("tel"));
		
		System.out.println("EmerJoinCommand");
		System.out.println(emergency_name);
		System.out.println(emergency_phonenum);
		System.out.println(emergency_email);
		System.out.println(alarm);
		System.out.println(alarmperiod);
		System.out.println(message);
		System.out.println(tel);
		
		ANDao dao=new ANDao();
		int state=dao.emerJoin(emergency_name,emergency_phonenum,
				emergency_email,alarm,alarmperiod,message,tel);
		
		//넘길 데이터의 이름과 값
		model.addAttribute("emerJoin", String.valueOf(state));
		
	}
	
	

}
