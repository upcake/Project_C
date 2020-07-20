package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.auto_medic.Dto.AlarmDTO;

import java.util.Calendar;

public class AlarmRingingActivity extends AppCompatActivity {
    AlarmManager alarm_Manager;
    Context context;
    PendingIntent pendingIntent;
    Button ringing_Start, ringing_Stop;
    //AlarmDTO dto = new AlarmDTO("a", "좀되라시발", "1", "0", "1", "0", "1", "0", "1", 2, 21, 30, 21, 31, -1, -1, 50, "기대", "1분");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);
        this.context = this;

        // 알람매니저 설정
        alarm_Manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Calendar 객체 생성
        final Calendar calendar = Calendar.getInstance();

        // 알람리시버 intent 생성
        final Intent my_intent = new Intent(this.context, AlarmReceiver.class);

        //시작 버튼
        ringing_Start = findViewById(R.id.ringing_Start);
        ringing_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //시간 찍어보기
               // int hour = dto.getAlarm_Ringtime1_Hour();
                //int minute = dto.getAlarm_Ringtime1_Minute();

                //calendar에 시간 셋팅
                //calendar.set(Calendar.HOUR_OF_DAY, dto.getAlarm_Ringtime1_Hour());
                //calendar.set(Calendar.MINUTE, dto.getAlarm_Ringtime1_Minute());

                //Receiver에 string 값 넘겨주기
                my_intent.putExtra("state", "alarm on");

                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //알람셋팅
                alarm_Manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        //알람 정지 버튼
        ringing_Stop = findViewById(R.id.ringing_Stop);
        ringing_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlarmRingingActivity.this, "Alarm 종료", Toast.LENGTH_SHORT).show();

                //알람 매니저 취소
                alarm_Manager.cancel(pendingIntent);

                my_intent.putExtra("state", "alarm off");

                //알람 취소
                sendBroadcast(my_intent);
            }
        });
    }
}
