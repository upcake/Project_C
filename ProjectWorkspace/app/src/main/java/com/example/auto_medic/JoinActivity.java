package com.example.auto_medic;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto_medic.ATask.JoinChkNickName;
import com.example.auto_medic.ATask.JoinChkEmail;
import com.example.auto_medic.ATask.JoinInsert;
import com.example.auto_medic.Dto.MemberDTO;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity implements  View.OnClickListener, Dialog.OnCancelListener {
    private static final String TAG = "mainJoinActivity";
    GMailSender sender = new GMailSender("automedic0724@gmail.com", "gksdnf123");

    String state;
    public static MemberDTO emailchkDTO = null;
    public static MemberDTO nicknamechkDTO = null;

    EditText join_etId, join_etPw, join_etPwChk, join_etNickName, join_etTel;
    Button join_btnJoin, join_btnIdChk, join_btnCancel, join_btnNickNameChk;
    TextView join_tvIdChk, join_tvPwChk2, join_tvNickNameChk, join_tvChkTel;
    int idchk = 0;
    int nicknamechk = 0;
    String chkidstr = "";
    String chknicknamestr = "";

    /*Dialog에 관련된 필드*/
    LayoutInflater dialog; //LayoutInflater
    View dialogLayout; //layout을 담을 View
    Dialog authDialog; //dialog 객체

    /*카운트 다운 타이머에 관련된 필드*/
    TextView time_counter; //시간을 보여주는 TextView
    EditText emailAuth_number; //인증 번호를 입력 하는 칸
    Button emailAuth_btn; // 인증버튼
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

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
        join_btnIdChk = findViewById(R.id.join_btnIdChk);
        join_btnCancel = findViewById(R.id.join_btnCancel);
        join_tvIdChk = findViewById(R.id.join_tvIdChk);
        join_tvPwChk2 = findViewById(R.id.join_tvPwChk2);
        join_btnNickNameChk = findViewById(R.id.join_btnNickNameChk);
        join_tvNickNameChk = findViewById(R.id.join_tvNickNameChk);

        //이메일 인증 버튼 클릭시 (하단 메서드참고)
        join_btnIdChk.setOnClickListener(this);

        //메일전송을 위한 준비
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        //아이디 edittext 입력후 키보드완료 버튼 클릭시 하단 textview에 경고 메세지
        join_etId.setOnKeyListener(new View.OnKeyListener() {
            @Override  // 키입력 처리 메서드
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String id = join_etId.getText().toString();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(id.length() == 0 ){  //아이디를 입력하지 않은 경우
                        join_tvIdChk.setText("아이디를 입력하세요!");
                        join_tvIdChk.setTextColor(Color.RED);
                        join_etId.setText("");
                        join_etId.requestFocus();
                        return false;
                    } else if (!checkEmail(id)) { //이메일 형식에 맞지 않는 경우
                        join_tvIdChk.setText("아이디는 이메일로 입력해주세요!");
                        join_tvIdChk.setTextColor(Color.RED);
                        join_etId.setText("");
                        join_etId.requestFocus();
                        return false;

                    } else {
                        //아이디 중복 체크
                        JoinChkEmail joinChkEmail = new JoinChkEmail(id);

                        try {
                            joinChkEmail.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (emailchkDTO != null) {
                            join_tvIdChk.setText("사용 불가능한 아이디 입니다!");
                            join_tvIdChk.setTextColor(Color.RED);
                            emailchkDTO = null;
                            return false;
                        } else {
                            join_tvIdChk.setText("인증하기 버튼을 눌러주세요!");
                            join_tvIdChk.setTextColor(Color.BLUE);
                            emailchkDTO = null;
                        }

                    }
                }
                return false;
            }
        });

        //비밀번호 일치 불일치 체크
        join_etPwChk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = join_etPw.getText().toString();
                String confirm = join_etPwChk.getText().toString();
                if(password.equals(confirm)){
                    join_tvPwChk2.setText("일치");
                    join_tvPwChk2.setTextColor(Color.BLUE);
                } else if(!password.equals(confirm)) {
                    join_tvPwChk2.setText("불일치");
                    join_tvPwChk2.setTextColor(Color.RED);
                }
                if(password.length()==0 || confirm.length()==0){
                    join_tvPwChk2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }

        });

        //닉네임 edittext 입력후 키보드완료 버튼 클릭시 하단 textview에 경고 메세지
        join_etNickName.setOnKeyListener(new View.OnKeyListener() {
            @Override  // 키입력 처리 메서드
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String nickname = join_etNickName.getText().toString();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(nickname.length() == 0 ){  //닉네임을 입력하지 않은 경우
                        join_tvNickNameChk.setText("닉네임을 입력하세요!");
                        join_tvNickNameChk.setTextColor(Color.RED);
                        join_etNickName.setText("");
                        join_etNickName.requestFocus();
                        return false;
                    } else if (!Pattern.matches("^[a-zA-Z0-9가-힣]{2,8}$", nickname)) { //닉네임 형식에 맞지 않는 경우
                        join_tvNickNameChk.setText("닉네임은 2~8자 영문 대소문자,\n숫자, 한글만 사용가능 합니다.");
                        join_tvNickNameChk.setTextColor(Color.RED);
                        join_etNickName.setText("");
                        join_etNickName.requestFocus();
                        return false;

                    } else {
                        //닉네임 중복 체크
                        JoinChkNickName joinChkNickName = new JoinChkNickName(nickname);

                        try {
                            joinChkNickName.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(nicknamechkDTO != null){
                            join_tvNickNameChk.setText("중복확인 버튼을 눌러주세요!");
                            join_tvNickNameChk.setTextColor(Color.RED); //사용이 불가한 닉네임에는 빨간색
                            nicknamechkDTO = null;
                            return false;
                        } else {
                            join_tvNickNameChk.setText("중복확인 버튼을 눌러주세요!");
                            join_tvNickNameChk.setTextColor(Color.BLUE); //사용이 가능한 닉네임에는 파란색
                            nicknamechkDTO = null;
                        }

                    }
                }
                return false;
            }
        });

        //닉네임 체크 : 중복확인 버튼을 누르면 정규식 및 중복확인되도록함
        join_btnNickNameChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = join_etNickName.getText().toString();

                if(nickname.length() == 0){
                    Toast.makeText(JoinActivity.this, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    join_tvNickNameChk.setText("닉네임을 입력하세요!");
                    join_tvNickNameChk.setTextColor(Color.RED);
                    join_etNickName.setText("");
                    join_etNickName.requestFocus();
                    return;
                } else if(!Pattern.matches("^[a-zA-Z0-9가-힣]{2,8}$", nickname)){
                    Toast.makeText(getApplicationContext(),"닉네임은 2~8자\n영소대문자, 숫자, 한글만 사용가능 합니다.",Toast.LENGTH_SHORT).show();
                    join_tvNickNameChk.setText("닉네임은 2~8자 영문 대소문자,\n숫자, 한글만 사용가능 합니다.");
                    join_tvNickNameChk.setTextColor(Color.RED);
                    join_etNickName.setText("");
                    join_etNickName.requestFocus();
                    return;
                } else {
                    Log.d(TAG, "onClick: "+nickname);
                    JoinChkNickName joinChkNickName = new JoinChkNickName(nickname);

                    try {
                        joinChkNickName.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(nicknamechkDTO != null){
                        Toast.makeText(JoinActivity.this, "사용 불가능한 닉네임 입니다!", Toast.LENGTH_SHORT).show();
                        nicknamechkDTO = null;
                        join_tvNickNameChk.setText("사용 불가능한 닉네임 입니다!");
                        join_tvNickNameChk.setTextColor(Color.RED);
                    } else  {
                        Toast.makeText(JoinActivity.this, "사용 가능한 닉네임 입니다!", Toast.LENGTH_SHORT).show();
                        join_tvNickNameChk.setText("사용 가능한 닉네임 입니다!");
                        join_tvNickNameChk.setTextColor(Color.BLUE);
                        nicknamechkDTO = null;
                        nicknamechk = 1;
                        chknicknamestr = nickname;
                    }
                }
            }
        });

       /*//전화번호 edittext 입력후 키보드완료 버튼 클릭시 하단 textview에 경고 메세지 (오류나서 구현불가)
        join_etTel.setOnClickListener(new View.OnClickListener() {
            String tel = join_etTel.getText().toString();
            @Override
            public void onClick(View v) {
                if(tel.length() == 0){  //전화번호를 입력하지 않은 경우
                    join_tvChkTel.setText("전화번호를 입력해주세요!");
                    join_tvChkTel.setTextColor(Color.RED);
                    join_etTel.requestFocus();
                    join_etTel.setText("");
                    return;
                } else if(!Pattern.matches("^[0-9]{10,11}$", tel)){
                    join_tvChkTel.setText("전화번호는 10-11자의 숫자만 사용가능합니다!");
                    join_tvChkTel.setTextColor(Color.RED);
                    join_etTel.requestFocus();
                    return;
                }
            }
        });*/

        //회원가입 버튼 클릭시
        join_btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String member_email = join_etId.getText().toString();
                String member_password = join_etPw.getText().toString();
                String member_nickname = join_etNickName.getText().toString();
                String member_phonenum =  join_etTel.getText().toString();

                //비밀번호 형식 체크
                if(join_etPw.getText().toString().length() == 0){   //비밀번호를 입력하지 않은경우
                    Toast.makeText(JoinActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    join_etPw.requestFocus();
                    return;
                } else if (join_etPw.getText().toString().length()<8 &&!isValidPassword(join_etPw.getText().toString())){   //비밀번호 형식에 맞지 않은 경우
                    Toast.makeText(getApplicationContext(), "올바른 비밀번호 형식이 아닙니다!\n비밀번호는 8자 이상, 알파벳 1개, 숫자 1개 및 특수 문자 1개를 포함해주세요!", Toast.LENGTH_SHORT).show();
                    join_etPw.setText("");
                    join_etPw.requestFocus();
                    return;
                }

                //비밀번호 확인 체크
                if(join_etPwChk.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "비밀번호 확인란을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    join_etPwChk.requestFocus();
                    return;
                } else if(!join_etPwChk.getText().toString().equals(join_etPw.getText().toString())){
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //전화번호 형식 체크
                String Tel = join_etTel.getText().toString();

               if(Tel.length() == 0){  //전화번호를 입력하지 않은 경우
                    Toast.makeText(JoinActivity.this, "전화번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    join_etTel.requestFocus();
                    return;
                } else if(!Pattern.matches("^[0-9]{10,11}$", Tel)){
                    Toast.makeText(JoinActivity.this, "전화번호는 10 ~ 11자의 숫자만 사용가능합니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //이메일 중복버튼이나 닉네임 중복버튼을 누르지 않으면 회원가입 진행 못하도록
                if(idchk == 0 && nicknamechk == 1){
                    Toast.makeText(JoinActivity.this, "아이디 인증하기 버튼을 클릭하여 메일인증을 진행해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(idchk == 1 && nicknamechk == 0){
                    Toast.makeText(JoinActivity.this, "닉네임 중복확인 버튼을 클릭해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(idchk == 0 && nicknamechk == 0){
                    Toast.makeText(JoinActivity.this, "아이디 인증하기 버튼 또는 닉네임 중복 버튼을 클릭해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(idchk == 1 && nicknamechk == 1 && !chkidstr.equals(join_etId.getText().toString()) && chknicknamestr.equals(join_etNickName.getText().toString()) ){
                    Toast.makeText(JoinActivity.this, "아이디 인증하기 버튼을 클릭하여 메일인증을 진행해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(idchk == 1 && nicknamechk == 1 && chkidstr.equals(join_etId.getText().toString()) && !chknicknamestr.equals(join_etNickName.getText().toString()) ){
                    Toast.makeText(JoinActivity.this, "닉네임 중복확인 버튼을 클릭해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(idchk == 1 && nicknamechk == 1 && !chkidstr.equals(join_etId.getText().toString()) && !chknicknamestr.equals(join_etNickName.getText().toString()) ) {
                    Toast.makeText(JoinActivity.this, "아이디 인증하기 버튼 또는 닉네임 중복 버튼을 클릭해주세요!", Toast.LENGTH_SHORT).show();
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
                    idchk = 0;
                    nicknamechk = 0;
                    chkidstr = "";
                    chknicknamestr = "";
                    finish();
                } else{
                    Toast.makeText(JoinActivity.this, "가입실패 !!!\n관리자에게 문의바랍니다.", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "가입실패 !!!");
                    return;
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

    public static boolean checkEmail(String email) {
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

    //메일 인증 버튼 클릭시 (메일 유효성 검사 및 중복검사 포함)

    @Override
    public void onClick(View v) {

        if(join_etId.getText().toString().length() == 0 ){  //아이디를 입력하지 않은 경우
            Toast.makeText(JoinActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
            join_tvIdChk.setText("아이디를 입력하세요!");
            join_tvIdChk.setTextColor(Color.RED);
            join_etId.requestFocus();
            return;
        } else if (!checkEmail(join_etId.getText().toString())) { //이메일 형식에 맞지 않는 경우
            Toast.makeText(getApplicationContext(), "올바른 아이디 형식이 아닙니다!\n아이디는 이메일로 입력해주세요!", Toast.LENGTH_SHORT).show();
            join_tvIdChk.setText("아이디는 이메일로 입력해주세요!");
            join_tvIdChk.setTextColor(Color.RED);
            join_etId.setText("");
            join_etId.requestFocus();
            return;
        } else {
            //아이디 중복 체크
            String id = join_etId.getText().toString();
            JoinChkEmail joinChkEmail = new JoinChkEmail(id);

            try {
                joinChkEmail.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(emailchkDTO != null){
                Toast.makeText(JoinActivity.this, "사용 불가능한 아이디 입니다!", Toast.LENGTH_SHORT).show();
                emailchkDTO = null;
                join_tvIdChk.setText("사용 불가능한 아이디 입니다!");
                join_tvIdChk.setTextColor(Color.RED);
                return;
            } else {
                join_tvIdChk.setText("메일 인증을 진행해주세요!");
                join_tvIdChk.setTextColor(Color.BLUE);
                Toast.makeText(JoinActivity.this, "이메일로 전송된 인증번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                emailchkDTO = null;

                //G메일 전송
                try {
                    sender.sendMail("오토메딕 - 이메일 인증을 진행해 주세요!",
                            "오토메딕 이메일 인증번호는 "+ sender.getEmailCode()+ "입니다. \n인증번호를 입력해주세요!",
                            "automedic0724@gmail.com",
                            id);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

            //인증 다이얼로그 생성
            dialog = LayoutInflater.from(this);
            dialogLayout = dialog.inflate(R.layout.auth_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
            authDialog = new Dialog(this); //Dialog 객체 생성
            authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
            authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
            authDialog.show(); //Dialog를 나타내어 준다.
            countDownTimer();

            //다이얼로그 내 인증번호 입력 확인버튼 클릭시
            emailAuth_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_answer = emailAuth_number.getText().toString();
                    if(user_answer.equals(sender.getEmailCode())){
                        Toast.makeText(getApplicationContext(), "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                        idchk = 1;
                        chkidstr = join_etId.getText().toString();
                        authDialog.cancel();
                    }else{
                        Toast.makeText(getApplicationContext(), "인증 번호를 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                        emailAuth_number.setText("");
                        emailAuth_number.requestFocus();
                        return;
                    }
                    join_tvIdChk.setText("이메일 인증성공!");
                    join_tvIdChk.setTextColor(Color.BLUE);
                }
            });
        }
    }

    public void countDownTimer() { //카운트 다운 메소드

        time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                authDialog.cancel();

            }
        }.start();

        emailAuth_btn.setOnClickListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    } //다이얼로그 닫을 때 카운트 다운 타이머의 cancel()메소드 호출


}
