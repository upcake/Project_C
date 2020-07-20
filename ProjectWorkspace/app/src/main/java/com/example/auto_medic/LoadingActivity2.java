package com.example.auto_medic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity2 extends AppCompatActivity {
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        textView2=findViewById(R.id.textView2);
        textView2.setText("일정 입력 중입니다...\n\n잠시만 기다려 주세요...");
        textView2.setTextSize(24);

        //로딩화면(스플래쉬)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), CalendarActivity.class);  // Intent 선언
                startActivity(intent);   // Intent 시작
                finish();
            }
        }, 1000);  // 로딩화면 시간

        Toast.makeText(this, "입력이 완료되었습니다 !!!", Toast.LENGTH_LONG).show();
    }
}
