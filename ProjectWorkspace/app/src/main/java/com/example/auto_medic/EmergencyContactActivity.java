package com.example.auto_medic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.auto_medic.ATask.EmerJoinInsert;
import com.example.auto_medic.ATask.EmerNameCheckAsync;

import java.util.concurrent.ExecutionException;

import static android.provider.ContactsContract.Contacts.CONTENT_URI;
import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;


public class EmergencyContactActivity extends AppCompatActivity {
    EditText name, phonenum, email;
    Button button1,button2;
    TextView namechk, phonechk;
    String state;
    int alarm=1;
    int alarmperiod=0;
    int message=1;
    int tel=1;


    public final int REQUEST_CODE=1004;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencycontact);

        name=findViewById(R.id.name);
        phonenum=findViewById(R.id.phonenum);
        email=findViewById(R.id.email);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);

        namechk=findViewById(R.id.namechk);
        phonechk=findViewById(R.id.phonechk);



        //주소록 창 띄우기
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, CONTENT_URI); //주소록 접근 CONTENT_URI: Contacts
                startActivityForResult(intent,REQUEST_CODE);//엑티비티간 데이터 전송

            }
        });

        //응급 주소 창 띄우기
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emergency_name = name.getText().toString();
                String emergency_phonenum = phonenum.getText().toString();
                String emergency_email = email.getText().toString();

                if (emergency_name.length()==0) {

                    namechk.setText("이름을 입력해주세요");
                    name.requestFocus();
                    phonechk.setText("");

                }else if(emergency_phonenum.length()<10 || emergency_phonenum.length()>11){

                    if(emergency_phonenum.length() == 0) {
                        phonechk.setText("전화번호를 입력해주세요");
                        phonenum.requestFocus();
                        namechk.setText("");


                    }else if(phonenum.getText().length() == 9){

                        if( !emergency_phonenum.substring(0,2).equals("02") ) {
                            phonechk.setText("형식에 알맞게 입력해주세요");
                            phonenum.requestFocus();
                            phonenum.setText("");
                            namechk.setText("");


                        }else{
                            if(isNetworkConnected(getApplicationContext()) == true){
                                eMernamecheck(emergency_name,emergency_phonenum);
                                namechk.setText("");
                                phonechk.setText("");
                                if(state.equals("1")){
                                    state="";
                                    eMerjoin(emergency_name, emergency_phonenum, emergency_email,
                                            alarm, alarmperiod, message, tel );

                                }


                            }else {
                                Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    }else{

                        phonechk.setText("형식에 알맞게 입력해주세요");
                        phonenum.requestFocus();
                        namechk.setText("");

                    }

                }else {

                    if(!emergency_phonenum.substring(0,3).equals("010") && !emergency_phonenum.substring(0,2).equals("02") && !emergency_phonenum.substring(0,3).equals("051") &&
                            !emergency_phonenum.substring(0,3).equals("053") && !emergency_phonenum.substring(0,3).equals("032") && !emergency_phonenum.substring(0,3).equals("062") &&
                            !emergency_phonenum.substring(0,3).equals("042") && !emergency_phonenum.substring(0,3).equals("052") && !emergency_phonenum.substring(0,3).equals("044") &&
                            !emergency_phonenum.substring(0,3).equals("031") && !emergency_phonenum.substring(0,3).equals("033") && !emergency_phonenum.substring(0,3).equals("043" )&&
                            !emergency_phonenum.substring(0,3).equals("063") && !emergency_phonenum.substring(0,3).equals("061") && !emergency_phonenum.substring(0,3).equals("054") &&
                            !emergency_phonenum.substring(0,3).equals("055") && !emergency_phonenum.substring(0,3).equals("064")  && !emergency_phonenum.substring(0,3).equals("041")){

                        phonechk.setText("형식에 알맞게 입력해주세요");
                        phonenum.requestFocus();
                        namechk.setText("");
                        phonenum.setText("");
                    }else {

                        if(isNetworkConnected(getApplicationContext()) == true){
                            eMernamecheck(emergency_name,emergency_phonenum);
                            namechk.setText("");
                            phonechk.setText("");
                            if(state.equals("1")){
                                state="";
                                eMerjoin(emergency_name, emergency_phonenum, emergency_email,
                                        alarm, alarmperiod, message, tel );

                            }


                        }else {
                            Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }



                    }
                }
            }
        });

    }

    private void eMernamecheck(String emergency_name ,String emergency_phonenum) {
        EmerNameCheckAsync emerNameCheckAsync=new EmerNameCheckAsync(emergency_name, emergency_phonenum);
        Log.d(TAG, "haClick: " + state);

        try {
            state = emerNameCheckAsync.execute().get().trim(); // 받은 걸 get 넣기: 로딩을 끝까지 기다릴 수 있음
            Log.d(TAG, "onClick: " + state);       //trim:공백 제거
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (state.equals("1")) {
            Log.d(TAG, "onClick: 중복안됨"+state);

        } else {
            Log.d(TAG, "onClick: 중복됨"+state);
            Toast.makeText(this, "이미 이름이나 전화번호가 등록되어 있습니다.", Toast.LENGTH_SHORT).show();

        }

    }

    private void eMerjoin(String emergency_name, String emergency_phonenum, String emergency_email,
                          int alarm,int alarmperiod, int message, int tel) {

        //db와 통신하여 집어넣기
        EmerJoinInsert emerjoinInsert = new EmerJoinInsert(emergency_name, emergency_phonenum, emergency_email,
                alarm, alarmperiod, message, tel   );
        Log.d(TAG, "haClick: " + state);

        try {
            state = emerjoinInsert.execute().get().trim(); // 받은 걸 get 넣기: 로딩을 끝까지 기다릴 수 있음
            Log.d(TAG, "onClick: " + state);       //trim:공백 제거
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (state.equals("1")) {
            Log.d(TAG, "onClick: 삽입성공");

        } else {
            Log.d(TAG, "onClick: 삽입실패");

        }

        Intent intent = new Intent(getApplicationContext(),
                EmergencyAddressActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //RequestCode는 MainActivity에서 sub Activity로 데이터 전송할 때 보낸 값.
        //resultCode는 반대 상황인 값
        if(resultCode==RESULT_OK){
            if(REQUEST_CODE==1004){
                Log.d(TAG, "onActivityResult: "+REQUEST_CODE);
                try{
                    Uri contactsUri=data.getData(); //선택한 정보(연락처)가 들어감
                    String id=contactsUri.getLastPathSegment(); //선택할 수 있는 화면,선택한 연락처의 id값 가져오기

                    getContacts(id); //id조회 메소드 만듦
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void getContacts(String id) {
        Cursor cursor=null;
        Cursor cursor1=null;
        String nname="";
        String pphone="";
        String eemail="";
        try{
            //  ContentResolver로 쿼리를 날림 -> resolver 가 provider 에게 쿼리하겠다고 요청
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, //참조할 uri
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",
                    new String[] { id },
                    null);
            cursor1=getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, //참조할 uri
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?",
                    new String[] { id },
                    null);

            if(cursor.moveToFirst()){
                //데이터가 있는 경우: 아이디를 이용하여 검색 이름, 전화, 메일을 찾아냄
                nname=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                name.setText(nname);
                pphone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        .replaceAll("-","");
                phonenum.setText(pphone);

            }
            if(cursor1.moveToFirst()){

                eemail=cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                email.setText(eemail);

            }
            cursor1.close();
            cursor.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
