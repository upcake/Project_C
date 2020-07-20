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
    String firmware="7.0.3";
    int num = 1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicesetting);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage1();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage2();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage3();
            }
        });

    }
    private void showMessage() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("확인창");
        builder.setMessage("정말로 데이터를 삭제하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DeviceSettingActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DeviceSettingActivity.this, "데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void showMessage1() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setMessage("현재 펌웨어 버전은 "+firmware+"입니다");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void showMessage2() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setMessage("현재 사용하고 계시는 블루투스 주소는 "+firmware+"입니다");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void showMessage3() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("확인창");

        if(num ==1) {
            builder.setMessage("기기등록을 하시겠습니까?");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DeviceSettingActivity.this, "기기등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    num +=1;
                }
            });


        }else{
            builder.setMessage("기기 등록 헤제를 하시겠습니까?");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DeviceSettingActivity.this, "기기등록이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    num=1;
                }
            });
        }
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DeviceSettingActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }





}
