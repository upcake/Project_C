package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto_medic.ATask.FindPwUpdate;
import com.example.auto_medic.ATask.JoinChkEmail;
import com.example.auto_medic.Dto.MemberDTO;

import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static com.example.auto_medic.JoinActivity.checkEmail;
import static com.example.auto_medic.JoinActivity.emailchkDTO;

public class LoginFindPw extends AppCompatActivity {

    public static MemberDTO findPwDTO = null;

    EditText loginFDPW_userEmail;
    Button loginFDPW_sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_find_pw);

        loginFDPW_userEmail = findViewById(R.id.loginFDPW_userEmail);
        loginFDPW_sendEmail = findViewById(R.id.loginFDPW_sendEmail);

        //임시 비밀번호 이메일 발송하기 버튼 클릭시
        loginFDPW_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String userEmail = loginFDPW_userEmail.getText().toString();

               if(userEmail.length() == 0){ //메일 주소를 입력하지 않았을때
                   Toast.makeText(getApplicationContext(), "메일주소를 입력하세요!", Toast.LENGTH_SHORT).show();
                   return;
               } else if (!checkEmail(userEmail)) { //아이디가 이메일 형식에 맞지 않는 경우
                   Toast.makeText(getApplicationContext(), "올바른 아이디 형식이 아닙니다!\n아이디는 이메일로 입력해주세요!", Toast.LENGTH_SHORT).show();
                   loginFDPW_userEmail.setText("");
                   loginFDPW_userEmail.requestFocus();
                    return;
               } else {
                   //아이디 중복 체크 (DB에 존재하지 않는 이메일 입력시 회원가입한 이메일로 입력하라고 안내)
                   JoinChkEmail joinChkEmail = new JoinChkEmail(userEmail);

                   try {
                       joinChkEmail.execute().get();
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   if (emailchkDTO == null) {
                       Toast.makeText(getApplicationContext(), "회원가입된 이메일 주소를 입력하세요!", Toast.LENGTH_SHORT).show();
                       emailchkDTO= null;
                       loginFDPW_userEmail.setText("");
                       loginFDPW_userEmail.requestFocus();
                       return;
                   } else {

                       GMailSender sender = new GMailSender("automedic0724@gmail.com", "gksdnf123");
                       try {
                           sender.sendMail("오토메딕 - 임시 비밀번호가 생성되었습니다!",
                                   "오토메딕 임시 비밀번호는 " + sender.getEmailCode() + "입니다. \n해당 번호로 로그인 후 비밀번호를 재설정 해주세요!!",
                                   "automedic0724@gmail.com",
                                   loginFDPW_userEmail.getText().toString());
                           Toast.makeText(getApplicationContext(), "이메일로 임시 비밀번호가 전송되었습니다.\n이메일을 확인해주세요...", Toast.LENGTH_SHORT).show();
                           Log.d("전송된이메일", "onClick: " + sender.getEmailCode());

                           //ATask 연결 (DB의 비밀번호를 임시비밀번호(sender.getEmailCode())로 변경)
                           FindPwUpdate findPwUpdate = new FindPwUpdate(userEmail, sender.getEmailCode());
                           try {
                               findPwUpdate.execute().get();
                           } catch (ExecutionException e) {
                               e.printStackTrace();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }

                       } catch (SendFailedException e) {
                           Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                       } catch (MessagingException e) {
                           Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                       emailchkDTO = null;
                   }
                   //이메일 발송버튼 클릭시 문제가 없다면 로그인 화면으로 전환

                   Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                   startActivity(intent);
                   finish();
               }
            }
        });
    }
}
