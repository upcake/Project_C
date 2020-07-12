package com.example.auto_medic.Dto;

import java.io.Serializable;

public class BlueAlarmDTO implements Serializable {
    CharSequence alarm_Ringtime1, alarm_Ringtime2, alarm_Ringtime3;
    int alarm_Volume;
    CharSequence alarm_Bell, alarm_Repeat;

    public BlueAlarmDTO() {
    }

    public BlueAlarmDTO(CharSequence alarm_Ringtime1, CharSequence alarm_Ringtime2, CharSequence alarm_Ringtime3, int alarm_Volume, CharSequence alarm_Bell, CharSequence alarm_Repeat) {
        this.alarm_Ringtime1 = alarm_Ringtime1;
        this.alarm_Ringtime2 = alarm_Ringtime2;
        this.alarm_Ringtime3 = alarm_Ringtime3;
        this.alarm_Volume = alarm_Volume;
        this.alarm_Bell = alarm_Bell;
        this.alarm_Repeat = alarm_Repeat;
    }

    public CharSequence getAlarm_Ringtime1() {
        return alarm_Ringtime1;
    }

    public void setAlarm_Ringtime1(CharSequence alarm_Ringtime1) {
        this.alarm_Ringtime1 = alarm_Ringtime1;
    }

    public CharSequence getAlarm_Ringtime2() {
        return alarm_Ringtime2;
    }

    public void setAlarm_Ringtime2(CharSequence alarm_Ringtime2) {
        this.alarm_Ringtime2 = alarm_Ringtime2;
    }

    public CharSequence getAlarm_Ringtime3() {
        return alarm_Ringtime3;
    }

    public void setAlarm_Ringtime3(CharSequence alarm_Ringtime3) {
        this.alarm_Ringtime3 = alarm_Ringtime3;
    }

    public int getAlarm_Volume() {
        return alarm_Volume;
    }

    public void setAlarm_Volume(int alarm_Volume) {
        this.alarm_Volume = alarm_Volume;
    }

    public CharSequence getAlarm_Bell() {
        return alarm_Bell;
    }

    public void setAlarm_Bell(CharSequence alarm_Bell) {
        this.alarm_Bell = alarm_Bell;
    }

    public CharSequence getAlarm_Repeat() {
        return alarm_Repeat;
    }

    public void setAlarm_Repeat(CharSequence alarm_Repeat) {
        this.alarm_Repeat = alarm_Repeat;
    }
}
