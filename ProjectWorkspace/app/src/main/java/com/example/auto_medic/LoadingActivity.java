package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //로딩화면(스플래쉬)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);  // Intent 선언
                startActivity(intent);   // Intent 시작
                finish();
            }
        }, 2000);  // 로딩화면 시간

        Toast.makeText(this, "로그인 되었습니다 !!!", Toast.LENGTH_LONG).show();
    }
}
