package com.example.auto_medic;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DeviceSettingActivity extends AppCompatActivity {
    Button button1,button2,button3,button4;
    String firmware="10.05.4";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicesetting);

        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("정말 데이터를 지우시겠습니까?");
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DeviceSettingActivity.this, "알겠다", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alert=builder.create();
                alert.setTitle("확인창");
                alert.show();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("현재 펌웨어 버전은 "+firmware+"입니다." );

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("현재 블루투스 주소는 "+firmware+"입니다." );

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeviceSettingActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });




    }
}