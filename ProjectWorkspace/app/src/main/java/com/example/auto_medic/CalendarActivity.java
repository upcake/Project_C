package com.example.auto_medic;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.example.auto_medic.ATask.MemoInsert;
import com.example.auto_medic.ATask.RecMedListAsync;
import com.example.auto_medic.Dto.RecMedDTO;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;


public class CalendarActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private static final String TAG = "Calendar:";
    CompactCalendarView calendar;
    TextView text, text2;
    Date date=new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy년 MM월");
    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<RecMedDTO> recmeds;
    ProgressDialog progressDialog;
    String Time="";
    Button time,input;
    EditText editText;

    int second=0, timechk=0, hu=0, address=0;
    String state="", hour="",min="",eventtime="", finaltime="", letter="";

    int mint= Color.parseColor("#BDF0FB");
    int blue=Color.parseColor("#8DCEFB");
    int rosepink=Color.parseColor("#FACBD3");
    int purple=Color.parseColor("#DEC2F6");
    int grey=Color.parseColor("#CFD7EC");
    int basic=Color.parseColor("#FFF8E1");

    int[] cclor={grey,rosepink,basic,mint,blue,purple};
    int sack=cclor[0];
    int[] img={R.drawable.sun4,R.drawable.eye,R.drawable.moon1};


    String email="aaaaa@naver.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        recmeds = new ArrayList<>();

        calendar=findViewById(R.id.calendar);
        text=findViewById(R.id.text);
        text2=findViewById(R.id.text2);
        time=findViewById(R.id.time);
        input=findViewById(R.id.input);
        editText=findViewById(R.id.editText);

        sdf.format(date);
        text2.setText(sdf1.format(date));



        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //시간 정하는 시간 프래그먼트 불러오기
                DialogFragment timepicker= new TimeFragment();
                timepicker.show(getSupportFragmentManager(),"oh");

            }
        });
        iNput(date); //일정을 입력하는 메소드



        String string = sdf.format(date)+
                "\n특정일에 대한 복용량을 알고 싶으시면 해당 날짜를 선택해 주세요\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
        int size = 78;

        //특정글자만 크게 만들기
        SpannableStringBuilder builder = new SpannableStringBuilder(string);
        //적용사이즈, 시작번째, 끝 번째
        builder.setSpan(new AbsoluteSizeSpan(size), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(builder);

        //1.일정리스트 불러오기
        try{
            if(isNetworkConnected(this) == true){
                RecMedListAsync recmedListAsync = new RecMedListAsync(recmeds, progressDialog);
                recmedListAsync.execute().get();
            }else {
                Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        //2.이벤트 설정하기(일정을 이벤트에 넣기)
        try {
            int a=recmeds.size();

            //시간을 초(timeInMillis)로 바꾸기
            for(int i=0; i<a;i++){
                long[] epoch=new long[a];
                epoch[i]+=(sdf2.parse(recmeds.get(i).getRecord_take_time()).getTime());
                //epoch[i]+=(sdf2.parse("2020-06-08 19:33:28").getTime()); items.size();


                //이벤트 설정하기 (깃허브): 달력에 표시됨
                Event[] ev=new Event[a];

                if(i>0){ //전의 일정과 같지 않으면 다른색깔로 표시하기
                    if(recmeds.get(i).getRecord_alarm_name().compareTo(recmeds.get(i-1).getRecord_alarm_name())!=0) {
                        if (hu  == 5) {
                            hu = 0;
                        } else {
                            hu += 1;
                        }
                        sack = cclor[hu];
                    }
                }
                //https://www.epochconverter.com/에서 날짜를 Timestamp in milliseconds에서 숫자로 바꾸어서 L 붙이기
                //색깔 , 시간     , 기록내용
                ev[i]= new Event(sack, epoch[i], recmeds.get(i).getRecord_alarm_name() );
                calendar.addEvent(ev[i]);


                //처음 날 기준으로 해서 붙이기
                List<Event> events = calendar.getEvents(epoch[0]);

            }
            //3.이벤트 있는 날짜 클릭 시 저장되어 있는 내용 나오게 하기
            calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(final Date date) {
                    finaltime="";
                    String medicine="";
                    int size = 78;
                    String string=sdf.format(date)+"\n";
                    SpannableStringBuilder builder = new SpannableStringBuilder(string);
                    builder.setSpan(new AbsoluteSizeSpan(size), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setText(builder);

                    iNput(date); //일정을 입력하는 메소드

                    //이벤트 있는 날짜 클릭 시 저장되어 있는 내용 나오게 하기
                    int a=recmeds.size();

                    for(int i=0; i<a;i++) {

                        String ttime = recmeds.get(i).getRecord_take_time();

                        if (sdf.format(date).compareTo(ttime.substring(0, 10)) == 0) { //비교해서 같을때 0을 반환
                            //sdf.format(date).compareTo("2020-06-08")==0
                            int hhour=Integer.parseInt(ttime.substring(11, 13));

                            //시간설정과 시간에 맞는 이미지 나오게 하기 위한 재료넣기
                            if(hhour>12){
                                hhour=hhour-12;
                                if(hhour<10){
                                    if(hhour>=6){
                                        letter="moon1";
                                        address=img[2];
                                    }else {
                                        letter = "eye";
                                        address=img[1];
                                    }
                                    eventtime="0"+hhour + ttime.substring(13, 16);

                                }else {
                                    letter="moon1";
                                    address=img[2];
                                    eventtime=hhour + ttime.substring(13, 16);
                                }
                            }else{
                                if(hhour<10){
                                    if(hhour>=6){
                                        letter="sun4";
                                        address=img[0];
                                    }else {
                                        letter="moon1";
                                        address=img[2];
                                    }
                                    eventtime="0"+hhour + ttime.substring(13, 16);
                                }else {
                                    letter="sun4";
                                    address=img[0];
                                    eventtime=hhour + ttime.substring(13, 16);
                                }
                            }
                            //List<Event> events = calendar.getEvents(date); : 그 날짜에 저장된 이벤트 정보가 나옴
                            //이벤트내용만 뽑아 쓸려 해도 같은 날은 내용이 겹치게 나오므로 DTO에 저장되어 있는 내용 쓰기
                            medicine = recmeds.get(i).getRecord_alarm_name();

                            string =" "+eventtime + " "+ medicine+"\n";

                            text.append(Htimage(letter,address)); //텍스트뷰에 이미지 넣는 메소드
                            text.append(" "+string);

                        }


                    }

                }

                @Override
                public void onMonthScroll(Date date) {
                    text2.setText(sdf1.format(date));
                }
            });


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private Spanned Htimage( final String letter, final int address) {

        //텍스트뷰에 이미지 넣기 Html이용
        Html.ImageGetter imageGetter = new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                if (source.equals(letter)) { //소스가 동일하면
                    //해당되는 안드로이드 그림 파일를 주소를 가져옴
                    Drawable drawable = getResources().getDrawable(address);
                    //크기조절
                    drawable.setBounds(0, -20,
                            drawable.getIntrinsicWidth()/7, drawable.getIntrinsicHeight()/10);

                    return drawable;
                }
                return null;
            }
        };
        // 원래 HTML은 img src가 인터넷 주소이거나, 해당 html 이 위치해있는 폴더를 기준으로 주소를 찾지만
        // 현재는 안드로이드 그림 파일을 보여주기 때문에 ImageGetter 를 구현해야함

        Spanned htmlText = Html.fromHtml("<img src=" +letter +" width=60 height=10>",
                imageGetter, null);
        //Spanned 는 CharSequence 를 implements 함
        return htmlText;

    }

    private void iNput(final Date date) {
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timechk==0) {
                    Toast.makeText(getApplicationContext(), "시간을 입력하세요", Toast.LENGTH_SHORT).show();
                }else if(editText.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "일정을 입력하세요", Toast.LENGTH_SHORT).show();
                }else {


                    String day=sdf.format(date)+" "+Time;
                    String memo=editText.getText().toString();
                    //db와 통신하여 집어넣기
                    MemoInsert memoInsert = new  MemoInsert(email,memo,day);


                    try {
                        state = memoInsert.execute().get().trim(); // 받은 걸 get 넣기: 로딩을 끝까지 기다릴 수 있음
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

                    timechk=0;
                    editText.setText("");
                    STart();

                }


            }
        });

    }

    private void STart() {
        Intent intent=new Intent(getApplicationContext(), LoadingActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                //기존에 있는 화면 사용 onNewIntent를 해서 select list을 함
                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
        startActivity(intent);
        finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(hourOfDay<10){
            hour= "0" + hourOfDay;
        }else{
            hour=String.valueOf(hourOfDay);
        }

        if(minute<10){
            min= "0" + minute;
        }else{
            min=String.valueOf(minute);
        }
        Time=hour+":"+min+":"+second+second;
        timechk=1;

    }

    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Sub1", "onNewIntent() 호출됨");

        //listView 갱신 안 시키면 오류남 갱신 시킨 것을 알려줌

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...

        recmeds.notifyAll();
        recmeds.clear();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            RecMedListAsync recmedListAsync = new RecMedListAsync(recmeds, progressDialog);
            try {
                recmedListAsync.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}


