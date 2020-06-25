package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto_medic.ATask.LoginSelect;
import com.example.auto_medic.Dto.MemberDTO;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    // 로그인이 성공하면 static 로그인DTO 변수에 담아서
    // 어느곳에서나 접근할 수 있게 한다
    public static MemberDTO loginDTO = null;
    
    EditText login_userID, login_userPw;
    TextView login_findPw;
    Button login_LoginBtn, login_JoinBtn, login_SocialLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkDangerousPermissions();

        login_userID = findViewById(R.id.login_userID);
        login_userPw = findViewById(R.id.login_userPw);

        login_findPw = findViewById(R.id.login_findPw);

        login_LoginBtn = findViewById(R.id.login_LoginBtn);
        login_JoinBtn = findViewById(R.id.login_JoinBtn);
        login_SocialLoginBtn = findViewById(R.id.login_SocialLoginBtn);

        //로그인 버튼 클릭
        login_LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_userID.getText().toString().length() != 0 && login_userPw.getText().toString().length() !=0){
                    String id = login_userID.getText().toString();
                    String passwd = login_userPw.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(id, passwd);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "아이디와 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디와 암호를 모두 입력하세요 !!!");
                    return;
                }

                if(loginDTO != null){
                    Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(getApplicationContext(), "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
                    //Log.d("main:login", loginDTO.getMember_nickname() + "님 로그인 되었습니다 !!!");


                }else {
                    Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디나 비밀번호가 일치안함 !!!");
                    login_userID.setText(""); login_userPw.setText("");
                    login_userID.requestFocus();
                }
            }
        });

        // 회원 가입 버튼
        login_JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 체크 버튼 (추후 스프링 연동)
        login_findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
