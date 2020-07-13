package com.example.auto_medic.Dto;

import java.io.Serializable;

public class AlarmDTO implements Serializable {
    CharSequence alarm_Email, alarm_Id,alarm_Title, alarm_Sunday, alarm_Monday, alarm_Tuesday;
    CharSequence alarm_Wednesday, alarm_Thursday, alarm_Friday, alarm_Saturday, alarm_Times;
    CharSequence alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour;
    CharSequence alarm_Ringtime3_Minute, alarm_Volume, alarm_Bell, alarm_Vib, alarm_Repeat;

    public AlarmDTO() {
    }

    public AlarmDTO(CharSequence alarm_Email, CharSequence alarm_Id, CharSequence alarm_Title, CharSequence alarm_Sunday, CharSequence alarm_Monday, CharSequence alarm_Tuesday, CharSequence alarm_Wednesday, CharSequence alarm_Thursday, CharSequence alarm_Friday, CharSequence alarm_Saturday, CharSequence alarm_Times, CharSequence alarm_Ringtime1_Hour, CharSequence alarm_Ringtime1_Minute, CharSequence alarm_Ringtime2_Hour, CharSequence alarm_Ringtime2_Minute, CharSequence alarm_Ringtime3_Hour, CharSequence alarm_Ringtime3_Minute, CharSequence alarm_Volume, CharSequence alarm_Bell, CharSequence alarm_Vib, CharSequence alarm_Repeat) {
        this.alarm_Email = alarm_Email;
        this.alarm_Id = alarm_Id;
        this.alarm_Title = alarm_Title;
        this.alarm_Sunday = alarm_Sunday;
        this.alarm_Monday = alarm_Monday;
        this.alarm_Tuesday = alarm_Tuesday;
        this.alarm_Wednesday = alarm_Wednesday;
        this.alarm_Thursday = alarm_Thursday;
        this.alarm_Friday = alarm_Friday;
        this.alarm_Saturday = alarm_Saturday;
        this.alarm_Times = alarm_Times;
        this.alarm_Ringtime1_Hour = alarm_Ringtime1_Hour;
        this.alarm_Ringtime1_Minute = alarm_Ringtime1_Minute;
        this.alarm_Ringtime2_Hour = alarm_Ringtime2_Hour;
        this.alarm_Ringtime2_Minute = alarm_Ringtime2_Minute;
        this.alarm_Ringtime3_Hour = alarm_Ringtime3_Hour;
        this.alarm_Ringtime3_Minute = alarm_Ringtime3_Minute;
        this.alarm_Volume = alarm_Volume;
        this.alarm_Bell = alarm_Bell;
        this.alarm_Vib = alarm_Vib;
        this.alarm_Repeat = alarm_Repeat;
    }

    public CharSequence getAlarm_Email() {
        return alarm_Email;
    }

    public void setAlarm_Email(CharSequence alarm_Email) {
        this.alarm_Email = alarm_Email;
    }

    public CharSequence getAlarm_Id() {
        return alarm_Id;
    }

    public void setAlarm_Id(CharSequence alarm_Id) {
        this.alarm_Id = alarm_Id;
    }

    public CharSequence getAlarm_Title() {
        return alarm_Title;
    }

    public void setAlarm_Title(CharSequence alarm_Title) {
        this.alarm_Title = alarm_Title;
    }

    public CharSequence getAlarm_Sunday() {
        return alarm_Sunday;
    }

    public void setAlarm_Sunday(CharSequence alarm_Sunday) {
        this.alarm_Sunday = alarm_Sunday;
    }

    public CharSequence getAlarm_Monday() {
        return alarm_Monday;
    }

    public void setAlarm_Monday(CharSequence alarm_Monday) {
        this.alarm_Monday = alarm_Monday;
    }

    public CharSequence getAlarm_Tuesday() {
        return alarm_Tuesday;
    }

    public void setAlarm_Tuesday(CharSequence alarm_Tuesday) {
        this.alarm_Tuesday = alarm_Tuesday;
    }

    public CharSequence getAlarm_Wednesday() {
        return alarm_Wednesday;
    }

    public void setAlarm_Wednesday(CharSequence alarm_Wednesday) {
        this.alarm_Wednesday = alarm_Wednesday;
    }

    public CharSequence getAlarm_Thursday() {
        return alarm_Thursday;
    }

    public void setAlarm_Thursday(CharSequence alarm_Thursday) {
        this.alarm_Thursday = alarm_Thursday;
    }

    public CharSequence getAlarm_Friday() {
        return alarm_Friday;
    }

    public void setAlarm_Friday(CharSequence alarm_Friday) {
        this.alarm_Friday = alarm_Friday;
    }

    public CharSequence getAlarm_Saturday() {
        return alarm_Saturday;
    }

    public void setAlarm_Saturday(CharSequence alarm_Saturday) {
        this.alarm_Saturday = alarm_Saturday;
    }

    public CharSequence getAlarm_Times() {
        return alarm_Times;
    }

    public void setAlarm_Times(CharSequence alarm_Times) {
        this.alarm_Times = alarm_Times;
    }

    public CharSequence getAlarm_Ringtime1_Hour() {
        return alarm_Ringtime1_Hour;
    }

    public void setAlarm_Ringtime1_Hour(CharSequence alarm_Ringtime1_Hour) {
        this.alarm_Ringtime1_Hour = alarm_Ringtime1_Hour;
    }

    public CharSequence getAlarm_Ringtime1_Minute() {
        return alarm_Ringtime1_Minute;
    }

    public void setAlarm_Ringtime1_Minute(CharSequence alarm_Ringtime1_Minute) {
        this.alarm_Ringtime1_Minute = alarm_Ringtime1_Minute;
    }

    public CharSequence getAlarm_Ringtime2_Hour() {
        return alarm_Ringtime2_Hour;
    }

    public void setAlarm_Ringtime2_Hour(CharSequence alarm_Ringtime2_Hour) {
        this.alarm_Ringtime2_Hour = alarm_Ringtime2_Hour;
    }

    public CharSequence getAlarm_Ringtime2_Minute() {
        return alarm_Ringtime2_Minute;
    }

    public void setAlarm_Ringtime2_Minute(CharSequence alarm_Ringtime2_Minute) {
        this.alarm_Ringtime2_Minute = alarm_Ringtime2_Minute;
    }

    public CharSequence getAlarm_Ringtime3_Hour() {
        return alarm_Ringtime3_Hour;
    }

    public void setAlarm_Ringtime3_Hour(CharSequence alarm_Ringtime3_Hour) {
        this.alarm_Ringtime3_Hour = alarm_Ringtime3_Hour;
    }

    public CharSequence getAlarm_Ringtime3_Minute() {
        return alarm_Ringtime3_Minute;
    }

    public void setAlarm_Ringtime3_Minute(CharSequence alarm_Ringtime3_Minute) {
        this.alarm_Ringtime3_Minute = alarm_Ringtime3_Minute;
    }

    public CharSequence getAlarm_Volume() {
        return alarm_Volume;
    }

    public void setAlarm_Volume(CharSequence alarm_Volume) {
        this.alarm_Volume = alarm_Volume;
    }

    public CharSequence getAlarm_Bell() {
        return alarm_Bell;
    }

    public void setAlarm_Bell(CharSequence alarm_Bell) {
        this.alarm_Bell = alarm_Bell;
    }

    public CharSequence getAlarm_Vib() {
        return alarm_Vib;
    }

    public void setAlarm_Vib(CharSequence alarm_Vib) {
        this.alarm_Vib = alarm_Vib;
    }

    public CharSequence getAlarm_Repeat() {
        return alarm_Repeat;
    }

    public void setAlarm_Repeat(CharSequence alarm_Repeat) {
        this.alarm_Repeat = alarm_Repeat;
    }
}