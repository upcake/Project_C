package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto_medic.ATask.JoinChkEmail;
import com.example.auto_medic.ATask.LoginSelect;
import com.example.auto_medic.Dto.MemberDTO;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static com.example.auto_medic.JoinActivity.checkEmail;
import static com.example.auto_medic.JoinActivity.emailchkDTO;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "mainLoginActivity";
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

        //비밀번호 찾기 클릭시 메일전송을 위한 준비
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        //로그인 버튼 클릭
        login_LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = login_userID.getText().toString();
                String passwd = login_userPw.getText().toString();

                if(id.length() == 0){  //아이디를 입력하지 않은 경우
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
                    login_userID.requestFocus();
                    return;
                }

                if (id.length() != 0 && passwd.length() == 0) {

                    if (!checkEmail(id)) { //아이디가 이메일 형식에 맞지 않는 경우
                        Toast.makeText(getApplicationContext(), "올바른 아이디 형식이 아닙니다!\n아이디는 이메일로 입력해주세요!", Toast.LENGTH_SHORT).show();
                        login_userID.setText("");
                        login_userID.requestFocus();
                        return;
                    }

                    //아이디 중복 체크 (DB에 존재하지 않는 이메일 입력시 회원가입 하도록 안내)
                    JoinChkEmail joinChkEmail = new JoinChkEmail(id);

                    try { 
                       joinChkEmail.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (emailchkDTO != null ) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                        login_userPw.requestFocus();
                        emailchkDTO = null;
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다\n회원가입을 진행해주세요!", Toast.LENGTH_SHORT).show();
                        emailchkDTO = null;
                        return;
                    }
                }

                //아이디와 비밀번호 모두 입력시
                if(id.length() != 0 && passwd.length() != 0){

                    if (!checkEmail(id)) { //이메일 형식에 맞지 않는 경우
                        Toast.makeText(getApplicationContext(), "올바른 아이디 형식이 아닙니다!\n아이디는 이메일로 입력해주세요!", Toast.LENGTH_SHORT).show();
                        login_userID.setText("");
                        login_userPw.setText("");
                        login_userID.requestFocus();
                        return;
                    }

                    LoginSelect loginSelect = new LoginSelect(id, passwd);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(loginDTO != null){
                        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                        startActivity(intent);
                        finish();
                        //Toast.makeText(getApplicationContext(), "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
                        //Log.d("main:login", loginDTO.getMember_nickname() + "님 로그인 되었습니다 !!!");

                    }else {
                        Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 일치하지 않습니다 !!!", Toast.LENGTH_SHORT).show();
                        login_userID.setText(""); login_userPw.setText("");
                        login_userID.requestFocus();
                    }

                }

            }

        });

        // 회원 가입 버튼 클릭시
        login_JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 버튼 클릭시 메일전송(새 액티비티로 이동)
        login_findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginFindPw.class);
                startActivity(intent);
            }



        });

        // 소셜로그인 버튼 클릭
        login_SocialLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), SocialLoginActivity.class);
                startActivity(intent);
            }
        });
    }



    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
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
