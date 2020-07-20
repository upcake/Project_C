package com.example.auto_medic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto_medic.ATask.InfoUpdate;
import com.example.auto_medic.Common.CommonMethod;
import com.example.auto_medic.Dto.AlarmDTO;
import com.example.auto_medic.Dto.InfoDTO;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends AppCompatActivity {
    private static final String TAG = "main:MyInfoActivity";

    //객체 선언
    CircleImageView myInfo_Profile;
    java.text.SimpleDateFormat tmpDateFormat;
    public String imageRealPathA, imageDbPathA;
    Bundle bundle;

    String myInfo_CurPw_Text;
    String myInfo_CurPw_Chk_Text;
    String myInfo_Pw_Text;
    String myInfo_Pw_Chk_Text;
    String myInfo_Pw_Re_Text;
    String myInfo_Pw_Re_Chk_Text;
    String myInfo_Phonenum_Text;
    String myInfo_Phonenum_Chk_Text;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    File file = null;
    long fileSize = 0;

    Button myInfo_Accept;
    TextView myInfo_CurPw_Chk, myInfo_Pw_Chk, myInfo_Pw_Re_Chk, myInfo_Phonenum_Chk;
    EditText myInfo_Pw, myInfo_Pw_Re, myInfo_CurPw, myInfo_Phonenum;


    InfoDTO infoDTO;
    String info_email, info_profile, info_pw, info_phonenum;


    CommonMethod commonMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        //객체 초기화
        myInfo_Profile = findViewById(R.id.myInfo_Profile);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        myInfo_CurPw = findViewById(R.id.myInfo_CurPw);
        myInfo_CurPw_Chk = findViewById(R.id.myInfo_CurPw_Chk);
        myInfo_Pw = findViewById(R.id.myInfo_Pw);
        myInfo_Pw_Chk = findViewById(R.id.myInfo_Pw_Chk);
        myInfo_Pw_Re = findViewById(R.id.myInfo_Pw_Re);
        myInfo_Pw_Re_Chk = findViewById(R.id.myInfo_Pw_Re_Chk);
        myInfo_Phonenum = findViewById(R.id.myInfo_Phonenum);
        myInfo_Phonenum_Chk = findViewById(R.id.myInfo_Phonenum_Chk);
        myInfo_Accept = findViewById(R.id.myInfo_Accept);

        myInfo_CurPw_Text = myInfo_CurPw.getText().toString();
        myInfo_CurPw_Chk_Text = myInfo_CurPw_Chk.getText().toString();
        myInfo_Pw_Chk_Text = myInfo_Pw_Chk.getText().toString();
        myInfo_Pw_Re_Text = myInfo_Pw_Re.getText().toString();
        myInfo_Pw_Re_Chk_Text = myInfo_Pw_Re_Chk.getText().toString();
        myInfo_Phonenum_Text = myInfo_Phonenum.getText().toString();
        myInfo_Phonenum_Chk_Text = myInfo_Phonenum_Chk.getText().toString();

        //정보 받기
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        infoDTO = (InfoDTO) bundle.getSerializable("infoDTO");
        Log.d(TAG, "onCreate: " + infoDTO.getInfo_email() + " / " + infoDTO.getInfo_pw() + " / " + infoDTO.getInfo_nickname() + " / " + infoDTO.getInfo_profile());

        //프사 변경
        myInfo_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
            }
        });

        //현재 비밀번호 일치 확인
        myInfo_CurPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //s : 현재 입력이 수행되고 있는 문자열
            //start : 변경이 문자열의 어느 위치였는지 인덱스로 알려줌
            //before : 변경이 수행되기 전 해당 위치의 문자열 길이
            //count : 변경이 수행된 문자열의 개수
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myInfo_CurPw_Text = myInfo_CurPw.getText().toString();
                if (!myInfo_CurPw_Text.equals(infoDTO.getInfo_pw())) {
                    if(myInfo_CurPw_Text.equals("")) {
                        Log.d(TAG, "onTextChanged: 빈칸" + s + " / " + infoDTO.getInfo_pw());
                        myInfo_CurPw_Chk.setText("현재 비밀번호를 입력하세요.");
                        myInfo_CurPw_Chk.setTextColor(Color.parseColor("#ED2000"));
                    } else {
                        Log.d(TAG, "onTextChanged: 빈칸" + s + " / " + infoDTO.getInfo_pw() + " / " + start + before + count);
                        myInfo_CurPw_Chk.setText("현재 비밀번호와 일치하지 않습니다.");
                        myInfo_CurPw_Chk.setTextColor(Color.parseColor("#ED2000"));
                    }
                } else {
                    Log.d(TAG, "onTextChanged: 빈칸" + s + " / " + infoDTO.getInfo_pw());
                    myInfo_CurPw_Chk.setText("현재 비밀번호와 일치합니다.");
                    myInfo_CurPw_Chk.setTextColor(Color.parseColor("#1EC800"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //새 비밀번호 유효성 검사
        myInfo_Pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //s : 현재 입력이 수행되고 있는 문자열
            //start : 변경이 문자열의 어느 위치였는지 인덱스로 알려줌
            //before : 변경이 수행되기 전 해당 위치의 문자열 길이
            //count : 변경이 수행된 문자열의 개수
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myInfo_Pw_Text = myInfo_Pw.getText().toString();
                if(myInfo_Pw_Text.equals("")) {
                    myInfo_Pw_Chk.setText("비밀번호를 입력하세요.");
                    myInfo_Pw_Chk.setTextColor(Color.parseColor("#ED2000"));
                } else {

                    myInfo_Pw_Re_Chk.setTextColor(Color.parseColor("#ED2000"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //새 비밀번호 일치 확인
        myInfo_Pw_Re.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //s : 현재 입력이 수행되고 있는 문자열
            //start : 변경이 문자열의 어느 위치였는지 인덱스로 알려줌
            //before : 변경이 수행되기 전 해당 위치의 문자열 길이
            //count : 변경이 수행된 문자열의 개수
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myInfo_Pw_Text = myInfo_Pw.getText().toString();
                myInfo_Pw_Re_Chk_Text = myInfo_Pw_Re_Chk.getText().toString();
                if (!myInfo_Pw_Re_Chk_Text.equals(myInfo_Pw_Text) && myInfo_Pw_Text.length() >= 1) {
                    if(myInfo_Pw_Re_Chk_Text.equals("")) {
                        myInfo_Pw_Re_Chk.setText("비밀번호를 입력하세요.");
                        myInfo_Pw_Re_Chk.setTextColor(Color.parseColor("#ED2000"));
                    } else {
                        myInfo_Pw_Re_Chk.setText("비밀번호가 일치하지 않습니다.");
                        myInfo_Pw_Re_Chk.setTextColor(Color.parseColor("#ED2000"));
                    }
                } else if(myInfo_Pw_Re_Chk_Text.equals(myInfo_Pw_Text) && myInfo_Pw_Text.length() >= 1) {
                    myInfo_Pw_Re_Chk.setText("비밀번호가 일치합니다.");
                    myInfo_Pw_Re_Chk.setTextColor(Color.parseColor("#1EC800"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //확인 버튼
        myInfo_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //비밀번호 유효성 검사
                if (myInfo_Pw_Text.equals(myInfo_Pw_Re_Text)) {
                    if(isValidPassword(myInfo_Pw_Re_Text)) {

                    }
                } else {

                }

                //전화번호 유효성 검사
                if(myInfo_Phonenum_Text.length()<10 || myInfo_Phonenum_Text.length()>11){
                    if(myInfo_Phonenum_Text.length() == 0) {
                        myInfo_Phonenum_Chk.setText("전화번호를 입력해주세요");
                        myInfo_Phonenum.requestFocus();
                    } else if(myInfo_Phonenum_Text.length() == 9) {
                        if( !myInfo_Phonenum_Text.substring(0,2).equals("02") ) {
                            myInfo_Phonenum_Chk.setText("형식에 알맞게 입력해주세요");
                            myInfo_Phonenum.requestFocus();
                            myInfo_Phonenum.setText("");
                        }
                    } else {
                        myInfo_Phonenum_Chk.setText("형식에 알맞게 입력해주세요");
                        myInfo_Phonenum.requestFocus();
                    }
                } else {
                    if(!myInfo_Phonenum_Text.substring(0,3).equals("010") && !myInfo_Phonenum_Text.substring(0,2).equals("02") && !myInfo_Phonenum_Text.substring(0,3).equals("051") &&
                            !myInfo_Phonenum_Text.substring(0,3).equals("053") && !myInfo_Phonenum_Text.substring(0,3).equals("032") && !myInfo_Phonenum_Text.substring(0,3).equals("062") &&
                            !myInfo_Phonenum_Text.substring(0,3).equals("042") && !myInfo_Phonenum_Text.substring(0,3).equals("052") && !myInfo_Phonenum_Text.substring(0,3).equals("044") &&
                            !myInfo_Phonenum_Text.substring(0,3).equals("031") && !myInfo_Phonenum_Text.substring(0,3).equals("033") && !myInfo_Phonenum_Text.substring(0,3).equals("043" )&&
                            !myInfo_Phonenum_Text.substring(0,3).equals("063") && !myInfo_Phonenum_Text.substring(0,3).equals("061") && !myInfo_Phonenum_Text.substring(0,3).equals("054") &&
                            !myInfo_Phonenum_Text.substring(0,3).equals("055") && !myInfo_Phonenum_Text.substring(0,3).equals("064")  && !myInfo_Phonenum_Text.substring(0,3).equals("041")) {
                        myInfo_Phonenum_Chk.setText("형식에 알맞게 입력해주세요");
                        myInfo_Phonenum.requestFocus();
                        myInfo_Phonenum.setText("");
                    }
                }

                info_email = "aaaaa@naver.com";
                info_pw = myInfo_Pw_Re_Text;
                info_phonenum = myInfo_Phonenum_Text;

                if(commonMethod.isNetworkConnected(view.getContext()) == true){
                    if(fileSize <= 31457280){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                        InfoUpdate infoUpdate = new InfoUpdate();
                        infoUpdate.execute();

                        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
                        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                        startActivity(showIntent);

                        finish();
                    } else {
                        // 알림창 띄움
                        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("알림");
                        builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else {
                    Toast.makeText(view.getContext(), "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }); //확인 버튼 온클릭리스너
    } //onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102 && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            info_profile = picturePath;
            myInfo_Profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    private File createFile() throws IOException {

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    //비밀번호 형식 체크 (암호는 최소 8자 이상, 알파벳 1개, 숫자 1개 및 특수 문자 1개를 사용)
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}

