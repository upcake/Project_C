package com.example.auto_medic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auto_medic.ATask.EmerDeleteAsync;
import com.example.auto_medic.ATask.EmerUpdate;
import com.example.auto_medic.ATask.EmerUpdate2;

import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;


public class AddressSettingActivity extends AppCompatActivity {
        TextView name,email;
        EditText phonenum;
        Button button1,button2,button3,button4,button5,button6,button7;
        int alarm=1;
        int alarmperiod=0;
        int message=1;
        int tel=1;
        int red= Color.parseColor("#EF0000");
        int blue= Color.parseColor("#4bc2fe");
        final CharSequence [] times ={"2분","3분","5분","7분","10분"};
        EmerDeleteAsync deleteEmerAsync;


        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_addresssetting);
            name=findViewById(R.id.name);
            phonenum=findViewById(R.id.phonenum);
            email=findViewById(R.id.email);

            button1=findViewById(R.id.button1);
            button2=findViewById(R.id.button2);
            button3=findViewById(R.id.button3);
            button4=findViewById(R.id.button4);
            button5=findViewById(R.id.button5);
            button6=findViewById(R.id.button6);
            button7=findViewById(R.id.button7);

            Intent intent=getIntent();
            processIntent(intent);

            if(alarm==1){
                button1.setBackgroundColor(red);

            }else{
                button1.setBackgroundColor(blue);

            }
            if(message==1){
                button3.setBackgroundColor(red);

            }else{
                button3.setBackgroundColor(blue);

            }
            if(tel==1){
                button4.setBackgroundColor(red);

            }else{
                button4.setBackgroundColor(blue);

            }



            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(alarm==1){
                        alarm +=1;
                        if(alarm==2) {
                            button1.setBackgroundColor(blue);
                            Toast.makeText(AddressSettingActivity.this, "알람이 해제되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        alarm=1;
                        if(alarm==1) {
                            button1.setBackgroundColor(red);
                            Toast.makeText(AddressSettingActivity.this, "알람이 설정되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alarmset();

                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(message==1){
                        message +=1;
                        if(message==2) {
                            Toast.makeText(AddressSettingActivity.this, "메세지가 해제되었습니다", Toast.LENGTH_SHORT).show();
                            button3.setBackgroundColor(blue);
                        }
                    }else{
                        message=1;
                        if(message==1) {
                            Toast.makeText(AddressSettingActivity.this, "메세지가 설정되었습니다", Toast.LENGTH_SHORT).show();
                            button3.setBackgroundColor(red);
                        }
                    }
                }
            });

            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tel==1){
                        tel +=1;
                        if(tel==2) {
                            Toast.makeText(AddressSettingActivity.this, "전화가 해제되었습니다", Toast.LENGTH_SHORT).show();
                            button4.setBackgroundColor(blue);
                        }
                    }else{
                        tel =1;
                        if(tel==1) {
                            Toast.makeText(AddressSettingActivity.this, "전화가 설정되었습니다", Toast.LENGTH_SHORT).show();
                            button4.setBackgroundColor(red);
                        }
                    }
                }
            });

            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    update();

                }
            });

            button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=getIntent();
                    String Name1=intent.getStringExtra("emergency_name");
                    delete(Name1);
                }
            });

            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNetworkConnected(getApplicationContext()) == true){
                        String Name=name.getText().toString();

                        EmerUpdate2 emerlistUpdate2 = new EmerUpdate2(
                                Name, alarm, alarmperiod, message, tel);
                        emerlistUpdate2.execute();
                    }else {
                        Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                    STart();
                }
            });


    }

    private void processIntent(Intent intent) {
            String emergency_name=intent.getStringExtra("emergency_name");
            String emergency_phonenum=intent.getStringExtra("emergency_phoneNum");
            String emergency_email=intent.getStringExtra("emergency_email");
            alarm= intent.getIntExtra("alarm", 1);  //숫자로 받을때 getIntExtra, defaultValue는 마음대로 정해도 됨
            alarmperiod= intent.getIntExtra("alarmperiod",1);
            message= intent.getIntExtra("message",1);
            tel= intent.getIntExtra("tel",1);

            name.setText(emergency_name);
            phonenum.setText(emergency_phonenum);
            email.setText(emergency_email);
            //Toast.makeText(this, ""+alarm+alarmperiod+message+tel, Toast.LENGTH_SHORT).show();

    }

    private void alarmset() {
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setTitle("알람 주기 설정");
        builder.setIcon(R.drawable.logo);
        builder.setSingleChoiceItems(times, alarmperiod, new DialogInterface.OnClickListener() { //배열(선택항목), 숫자:선택된 번째
                   @Override
                   public void onClick(DialogInterface dialog, int index) { //배열, 숫자  숫자는 항목 번째 수
                       alarmperiod=index;
                   }
               });
       builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int index) {
               index=alarmperiod;
           }
       });
        Toast.makeText(this, ""+alarmperiod, Toast.LENGTH_SHORT).show();
        AlertDialog dialog=builder.create();
        dialog.show();

    }
    private void update() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("확인창");
        builder.setMessage("이대로 수정하시겠습니까?");
        builder.setIcon(R.drawable.logo);


        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Name=name.getText().toString();
                String Phone=phonenum.getText().toString();

                 if(Phone.length()<10 || Phone.length()>11) {

                    if (Phone.equals("")) {
                        Toast.makeText(AddressSettingActivity.this,
                                "전화번호를 입력하십시오", Toast.LENGTH_SHORT).show();
                        phonenum.requestFocus();
                    } else {
                        Toast.makeText(AddressSettingActivity.this,
                                "형식에 맞게 입력하십시오", Toast.LENGTH_SHORT).show();
                        phonenum.requestFocus();
                    }
                }else if(Phone.length() ==9){
                    if( !Phone.substring(0,2).equals("02") ) {
                        Toast.makeText(AddressSettingActivity.this,
                                "형식에 맞게 입력하십시오", Toast.LENGTH_SHORT).show();

                    }else{
                        if(isNetworkConnected(getApplicationContext()) == true){

                            EmerUpdate emerlistUpdate = new EmerUpdate(Name,Phone,
                                                       alarm, alarmperiod, message, tel);
                            emerlistUpdate.execute();



                        }else {
                            Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }

                         Toast.makeText(AddressSettingActivity.this,
                                    "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                         STart();

                        }

                }else {

                    if(!Phone.substring(0,3).equals("010")&& !Phone.substring(0,2).equals("02") && !Phone.substring(0,3).equals("051") &&
                            !Phone.substring(0,3).equals("053") && !Phone.substring(0,3).equals("032") && !Phone.substring(0,3).equals("062") &&
                            !Phone.substring(0,3).equals("042") && !Phone.substring(0,3).equals("052") && !Phone.substring(0,3).equals("044") &&
                            !Phone.substring(0,3).equals("031") && !Phone.substring(0,3).equals("033") && !Phone.substring(0,3).equals("043" )&&
                            !Phone.substring(0,3).equals("063") && !Phone.substring(0,3).equals("061") && !Phone.substring(0,3).equals("054") &&
                            !Phone.substring(0,3).equals("055") && !Phone.substring(0,3).equals("064")  && !Phone.substring(0,3).equals("041")) {

                        Toast.makeText(AddressSettingActivity.this,
                                "형식에 맞게 입력하십시오", Toast.LENGTH_SHORT).show();
                        phonenum.requestFocus();
                    }else{

                        if(isNetworkConnected(getApplicationContext()) == true){

                            EmerUpdate emerlistUpdate = new EmerUpdate(Name,Phone,
                                                        alarm, alarmperiod, message, tel);
                            emerlistUpdate.execute();



                        }else {
                            Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(AddressSettingActivity.this,
                                "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        STart();


                    }
                }
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    private void STart() {
        Intent intent=new Intent(getApplicationContext(),EmergencyAddressActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
        startActivity(intent);
        finish();
    }

    private void delete(final String Name1) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("확인창");
        builder.setMessage("정말로 삭제하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isNetworkConnected(getApplicationContext()) == true){
                    deleteEmerAsync = new EmerDeleteAsync(Name1);
                    deleteEmerAsync.execute();

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(AddressSettingActivity.this,
                        "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                STart();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
