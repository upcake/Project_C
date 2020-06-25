package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyInfoActivity extends AppCompatActivity {
    //객체 선언
    Button myInfo_Profile, myInfo_Accept;
    EditText myInfo_Password, myInfo_Password_Re, myInfo_Nickname, myInfo_Phonenum;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        //객체 초기화
        myInfo_Profile = findViewById(R.id.myInfo_Profile);
        myInfo_Accept = findViewById(R.id.myInfo_Accept);
        myInfo_Password = findViewById(R.id.myInfo_Password);
        myInfo_Password_Re = findViewById(R.id.myInfo_Password_Re);
        myInfo_Nickname = findViewById(R.id.myInfo_Nickname);
        myInfo_Phonenum = findViewById(R.id.myInfo_Phonenum);

        //확인 버튼
        myInfo_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.putExtra("navCnt", 3);
                finish();
            }
        });
    }
}
