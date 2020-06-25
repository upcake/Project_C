package com.example.auto_medic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AlarmFragment extends Fragment {
    //객체 선언
    TextView alarmF_NextAlarm_Title, alarmF_NextAlarm, alarmF_SetBluetooth;
    ImageButton alarmF_BlueOnOff;
    int blueCnt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //XML, java 연결
        //XML이 메인에 직접 붙으면 true, 프래그먼트에 붙으면 false
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);

        //객체 초기화
        alarmF_NextAlarm_Title = rootView.findViewById(R.id.alarmF_NextAlarm_Title);
        alarmF_NextAlarm = rootView.findViewById(R.id.alarmF_NextAlarm);
        alarmF_SetBluetooth = rootView.findViewById(R.id.alarmF_SetBluetooth);
        alarmF_BlueOnOff = rootView.findViewById(R.id.alarmF_BlueOnOff);

        //블루투스 알람 설정 클릭 시
        alarmF_SetBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //블루투스 On/Off 클릭 시 아이콘 바꾸기
        blueCnt = 1; //0 : 꺼진 상태, 1 : 켜진 상태
        alarmF_BlueOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blueCnt == 1) {
                    alarmF_BlueOnOff.setImageResource(R.drawable.bluetooth_off);
                    alarmF_BlueOnOff.setBackgroundColor(Color.argb(37,00,00,00));
                    blueCnt = 0;
                } else if(blueCnt == 0) {
                    alarmF_BlueOnOff.setImageResource(R.drawable.bluetooth_on);
                    alarmF_BlueOnOff.setBackgroundColor(Color.rgb(00,68,145));
                    blueCnt = 1;
                }
            }
        });

       return rootView;
    }
}

