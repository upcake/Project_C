package com.csslect.app.dto;

public class RecMedDTO {
	String record_email,record_alarm_name,record_take_time;

	public RecMedDTO(String record_email, String record_alarm_name, String record_take_time) {
		super();
		this.record_email = record_email;
		this.record_alarm_name = record_alarm_name;
		this.record_take_time = record_take_time;
	}

	public String getRecord_email() {
		return record_email;
	}

	public void setRecord_email(String record_email) {
		this.record_email = record_email;
	}

	public String getRecord_alarm_name() {
		return record_alarm_name;
	}

	public void setRecord_alarm_name(String record_alarm_name) {
		this.record_alarm_name = record_alarm_name;
	}

	public String getRecord_take_time() {
		return record_take_time;
	}

	public void setRecord_take_time(String record_take_time) {
		this.record_take_time = record_take_time;
	}
	
	
	

}
