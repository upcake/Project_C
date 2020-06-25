package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto_medic.ATask.JoinInsert;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    String state;

    EditText join_etId, join_etPw, join_etPwChk, join_etNickName, join_etTel;
    Button join_btnJoin, join_IdChkBtn, join_btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_etId = findViewById(R.id.join_etId);
        join_etPw = findViewById(R.id.join_etPw);
        join_etPwChk = findViewById(R.id.join_etPwChk);
        join_etNickName = findViewById(R.id.join_etNickName);
        join_etTel = findViewById(R.id.join_etTel);
        join_btnJoin = findViewById(R.id.join_btnJoin);
        join_IdChkBtn = findViewById(R.id.join_IdChkBtn);
        join_btnCancel = findViewById(R.id.join_btnCancel);

        //회원가입 버튼 클릭시
        join_btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String member_email = join_etId.getText().toString();
                String member_password = join_etPw.getText().toString();
                String member_nickname = join_etNickName.getText().toString();
                String member_phonenum =  join_etTel.getText().toString();
                String member_chkpw = join_etPwChk.getText().toString();

                //아이디(이메일) 형식 체크
                if(join_etId.getText().toString() != null){
                    if (!checkEmail(join_etId.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다!", Toast.LENGTH_SHORT).show();
                    }
                }

                //아이디 체크 버튼 클릭시 메일링(추후 스프링 배워서 수정)
                join_IdChkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                //비밀번호 형식 체크
                if(join_etPw.getText().toString().length()<8 &&!isValidPassword(join_etPw.getText().toString())){
                    Toast.makeText(getApplicationContext(), "올바른 비밀번호 형식이 아닙니다!\n비밀번호는 8자 이상, 알파벳 1개, 숫자 1개 및 특수 문자 1개를 포함해주세요!", Toast.LENGTH_SHORT).show();
                }



                // 아이디, 비밀번호, 비밀번호 체크, 닉네임, 전화번호중 하나라도 빈칸이면 전송 안되도록 함
                if(member_email.length() == 0 || member_password.length() == 0 || member_chkpw.length() == 0 || member_nickname.length() == 0 || member_phonenum.length() == 0 ){
                    return;
                }

                JoinInsert joinInsert = new JoinInsert(member_email, member_password, member_nickname, member_phonenum );
                try {
                    state = joinInsert.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "가입성공 !!!\n로그인을 진행해주세요!", Toast.LENGTH_LONG).show();
                    Log.d("main:joinact", "가입성공 !!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "가입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "가입실패 !!!");
                    finish();
                }
            }

        });

        join_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //이메일 형식 체크
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    //비밀번호 형식 체크 (암호는 최소 8자 이상, 알파벳 1개, 숫자 1개 및 특수 문자 1개를 사용)
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}
