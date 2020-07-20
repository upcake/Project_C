package com.example.auto_medic.Dto;

import java.io.Serializable;

public class EmergencyDTO implements Serializable {

    public String emergency_name;
    public String emergency_phonenum;
    public String emergency_email;
    public int alarm;
    public int alarmperiod;
    public int message;
    public int tel;


    //public EmergencyDTO(){};


    public EmergencyDTO(String emergency_name, String emergency_phonenum, String emergency_email, int alarm, int alarmperiod, int message, int tel) {
        this.emergency_name = emergency_name;
        this.emergency_phonenum = emergency_phonenum;
        this.emergency_email = emergency_email;
        this.alarm = alarm;
        this.alarmperiod = alarmperiod;
        this.message = message;
        this.tel = tel;
    }

    public String getEmergency_name() {
        return emergency_name;
    }

    public void setEmergency_name(String emergency_name) {
        this.emergency_name = emergency_name;
    }

    public String getEmergency_phonenum() {
        return emergency_phonenum;
    }

    public void setEmergency_phonenum(String emergency_phonenum) {
        this.emergency_phonenum = emergency_phonenum;
    }

    public String getEmergency_email() {
        return emergency_email;
    }

    public void setEmergency_email(String emergency_email) {
        this.emergency_email = emergency_email;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public int getAlarmperiod() {
        return alarmperiod;
    }

    public void setAlarmperiod(int alarmperiod) {
        this.alarmperiod = alarmperiod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }
}
