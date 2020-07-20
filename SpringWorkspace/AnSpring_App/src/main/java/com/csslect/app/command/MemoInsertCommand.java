package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;


public class MemoInsertCommand implements ACommand{

	@Override
	public void execute(Model model) {
		String record_email=(String)model.asMap().get("record_email");
		String record_alarm_name=(String)model.asMap().get("record_alarm_name");
		String record_take_time=((String) model.asMap().get("record_take_time"));		
		
		
		System.out.println("EmerJoinCommand");
		System.out.println(record_email);
		System.out.println(record_alarm_name);
		System.out.println(record_take_time);
		
		
		ANDao dao=new ANDao();
		int state=dao.memoinsert(record_email,record_alarm_name,
				record_take_time);
		
		//넘길 데이터의 이름과 값
		model.addAttribute("emerJoin", String.valueOf(state));
		
	}
	
	

}
