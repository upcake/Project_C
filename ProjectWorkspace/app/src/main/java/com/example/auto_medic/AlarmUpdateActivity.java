package com.example.auto_medic;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.auto_medic.ATask.AlarmUpdate;
import com.example.auto_medic.Dto.AlarmDTO;

public class AlarmUpdateActivity extends AppCompatActivity {

    //객체 선언//
    private static final String TAG = "AlarmUpdateActivity";

    //받아온 데이터
    CharSequence alarm_Email, alarm_Id,alarm_Title, alarm_Sunday, alarm_Monday, alarm_Tuesday;
    CharSequence alarm_Wednesday, alarm_Thursday, alarm_Friday, alarm_Saturday, alarm_Times;
    CharSequence alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour;
    CharSequence alarm_Ringtime3_Minute, alarm_Volume, alarm_Bell, alarm_Vib, alarm_Repeat;

    //이름 설정
    ConstraintLayout setName_Parent;
    EditText setName_Name;
    InputMethodManager imm;

    //요일 설정
    ToggleButton day_Sun, day_Mon, day_Tue, day_Wed, day_Thu, day_Fri, day_Sat;

    //횟수 설정
    RadioGroup times_Parent;
    ToggleButton times_one, times_two, times_three;
    CharSequence timesCnt;

    //시간 설정
    Button setTime_1, setTime_2, setTime_3;

    //볼륨 조절
    ConstraintLayout vol_Parent;
    SeekBar vol_SeekBar;

    //벨소리 조절
    ConstraintLayout bell_Parent;
    TextView bell_Name;
    String ringToneName;
    String ringTonePath;
    Uri uri;

    //진동 조절
    ConstraintLayout vib_Parent;
    Switch vib_Switch;
    CharSequence vibCnt;

    //다시 울림 설정
    ConstraintLayout repeat_Parent;
    TextView repeat_Time;
    final CharSequence [] snoozeTime ={"사용 안함", "1분","5분","10분","15분","20분", "25분", "30분"};
    int snoozeCnt = 0;
    int snoozeVal;

    //확인 버튼
    ConstraintLayout confirm_Parent;
    AlarmDTO dto;
    Bundle alarm_Bundle;
    String state;

    //취소 버튼
    ConstraintLayout cancel_Parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        //보내온 값 파싱
        Intent intent = getIntent();
        AlarmDTO alarmDTO = (AlarmDTO) intent.getSerializableExtra("selItem");

        alarm_Email = alarmDTO.getAlarm_Email();
        alarm_Id = alarmDTO.getAlarm_Id();
        alarm_Title = alarmDTO.getAlarm_Title();
        alarm_Sunday = alarmDTO.getAlarm_Sunday();
        alarm_Monday = alarmDTO.getAlarm_Monday();
        alarm_Tuesday = alarmDTO.getAlarm_Tuesday();

        alarm_Wednesday = alarmDTO.getAlarm_Wednesday();
        alarm_Thursday = alarmDTO.getAlarm_Thursday();
        alarm_Friday = alarmDTO.getAlarm_Friday();
        alarm_Saturday = alarmDTO.getAlarm_Saturday();
        alarm_Times = alarmDTO.getAlarm_Times();

        alarm_Ringtime1_Hour = alarmDTO.getAlarm_Ringtime1_Hour();
        alarm_Ringtime1_Minute = alarmDTO.getAlarm_Ringtime1_Minute();
        alarm_Ringtime2_Hour = alarmDTO.getAlarm_Ringtime2_Hour();
        alarm_Ringtime2_Minute = alarmDTO.getAlarm_Ringtime2_Minute();
        alarm_Ringtime3_Hour = alarmDTO.getAlarm_Ringtime3_Hour();

        alarm_Ringtime3_Minute = alarmDTO.getAlarm_Ringtime3_Minute();
        alarm_Volume = alarmDTO.getAlarm_Volume();
        alarm_Bell = alarmDTO.getAlarm_Bell();
        alarm_Vib = alarmDTO.getAlarm_Vib();
        alarm_Repeat = alarmDTO.getAlarm_Repeat();

        //이름 설정////////////////////////////////
        //객체 초기화
        setName_Parent = findViewById(R.id.setName_Parent);
        setName_Name = findViewById(R.id.setName_Name);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setName_Name.setText(alarm_Title);

        //이름 설정 칸 클릭 시 이름 입력 칸으로 포커스, 키보드 표시
        setName_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setName_Name.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        //이름 설정 칸에서 엔터가 '완료'로, 커서, 포커스 사라지고 키보드 내려가게 설정
        setName_Name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_DONE:
                        imm.hideSoftInputFromWindow(setName_Name.getWindowToken(), 0);
                        setName_Parent.requestFocus();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        //요일 설정////////////////////////////////
        //객체 초기화
        day_Sun = findViewById(R.id.day_Sun);
        day_Mon = findViewById(R.id.day_Mon);
        day_Tue = findViewById(R.id.day_Tue);
        day_Wed = findViewById(R.id.day_Wed);
        day_Thu = findViewById(R.id.day_Thu);
        day_Fri = findViewById(R.id.day_Fri);
        day_Sat = findViewById(R.id.day_Sat);

        final ToggleButton[] days = {day_Sun, day_Mon, day_Tue, day_Wed, day_Thu, day_Fri, day_Sat};
        final CharSequence[] dayCnt = new CharSequence[7];
        dayCnt[0] = alarm_Sunday;
        dayCnt[1] = alarm_Monday;
        dayCnt[2] = alarm_Tuesday;
        dayCnt[3] = alarm_Wednesday;
        dayCnt[4] = alarm_Thursday;
        dayCnt[5] = alarm_Friday;
        dayCnt[6] = alarm_Saturday;

        for (int i = 0; i < dayCnt.length; i++) {
            if(dayCnt[i].toString().equals("1")) {
                days[i].setChecked(true);
            }
        }

        for (int i = 0; i < days.length; i++) {
            final int finalI = i;
            days[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (toggleCheck(days[finalI]) == null) {
                        dayCnt[finalI] = "0";
                    } else {
                        dayCnt[finalI] = toggleCheck(days[finalI]);
                    }
                    Toast.makeText(AlarmUpdateActivity.this, "days : " + finalI + ", dayCnt : " + dayCnt[finalI], Toast.LENGTH_SHORT).show();
                }
            });
        }

        //횟수 설정///////////////////////////////////////
        //객체 초기화
        times_Parent = findViewById(R.id.times_Parent);
        times_one = findViewById(R.id.times_one);
        times_two = findViewById(R.id.times_two);
        times_three = findViewById(R.id.times_three);
        timesCnt = alarm_Times;

        if(timesCnt.toString().equals("1")) {
            times_one.setChecked(true);
        } else if(timesCnt.toString().equals("2")) {
            times_one.setChecked(false);
            times_two.setChecked(true);
        } else if(timesCnt.toString().equals("3")) {
            times_one.setChecked(false);
            times_two.setChecked(false);
            times_three.setChecked(true);
        } else {
            times_one.setChecked(false);
        }

        times_Parent.setOnCheckedChangeListener(ToggleListener);

        //시간 설정////////////////////////////////////////////
        //객체 초기화
        setTime_1 = findViewById(R.id.setTime_1);
        setTime_2 = findViewById(R.id.setTime_2);
        setTime_3 = findViewById(R.id.setTime_3);

        //시간 버튼 설정
        if(times_one.isChecked()) {
            timesCnt = "1";
            setTime_1.setEnabled(true);
            setTime_2.setEnabled(false);
            setTime_3.setEnabled(false);
        } else if(times_two.isChecked()) {
            timesCnt = "2";
            setTime_1.setEnabled(true);
            setTime_2.setEnabled(true);
            setTime_3.setEnabled(false);
        } else if(times_three.isChecked()) {
            timesCnt = "3";
            setTime_1.setEnabled(true);
            setTime_2.setEnabled(true);
            setTime_3.setEnabled(true);
        } else {
            timesCnt = "0";
            setTime_1.setEnabled(false);
            setTime_2.setEnabled(false);
            setTime_3.setEnabled(false);
        }

        //시간 받아온 시간으로 설정
        setTime_1.setText(alarm_Ringtime1_Hour + ":" + alarm_Ringtime1_Minute);
        setTime_2.setText(alarm_Ringtime2_Hour + ":" + alarm_Ringtime2_Minute);
        setTime_3.setText(alarm_Ringtime3_Hour + ":" + alarm_Ringtime3_Minute);

        //시간 텍스트로 쪼개기
        final Button[] setTimeButtons = {setTime_1, setTime_2, setTime_3};
        final String[][] timeTexts = new String[3][2];
        for(int i = 0; i < timeTexts.length; i++) {
            timeTexts[i] = String.valueOf(setTimeButtons[i].getText()).split(":");
        }

        //버튼 클릭 시
        final int[] setTimeCnt = {0, 0, 0};

        for(int i = 0; i < setTimeButtons.length; i++) {
            final int finalI = i;
            setTimeButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog timePickerDialog
                            = new TimePickerDialog(AlarmUpdateActivity.this, timeSetListeners[finalI], Integer.parseInt(timeTexts[finalI][0]), Integer.parseInt(timeTexts[finalI][1]), true);
                    timePickerDialog.show();

                    timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            timeTexts[finalI] = String.valueOf(setTimeButtons[finalI].getText()).split(":");
                            setTimeCnt[finalI] += 1;
                        }
                    });
                }
            });
        }

        //볼륨 설정////////////////////////////////////////////
        vol_Parent = findViewById(R.id.vol_Parent);
        vol_SeekBar = findViewById(R.id.vol_SeekBar);
        final CharSequence[] volume = {alarm_Volume};
        vol_SeekBar.setProgress(Integer.parseInt(alarm_Volume.toString()));

        vol_Parent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(volume[0] == "0") {
                    vol_SeekBar.setProgress(100, true);
                    volume[0] = "100";
                } else {
                    vol_SeekBar.setProgress(0, true);
                    volume[0] = "0";
                }
            }
        });

        vol_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                volume[0] = (CharSequence) String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //벨소리 설정////////////////////////////////////////////
        bell_Parent = findViewById(R.id.bell_Parent);
        bell_Name = findViewById(R.id.bell_Name);
        bell_Name.setText(alarm_Bell);

        bell_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "벨소리 선택");
                startActivityForResult(intent, 101);
            }
        });

        //진동 설정////////////////////////////////////////////
        vib_Parent = findViewById(R.id.vib_Parent);
        vib_Switch = findViewById(R.id.vib_Switch);
        vibCnt = alarm_Vib;
        if(alarm_Vib.toString().equals("1")) { vib_Switch.setChecked(true); }
        else { vib_Switch.setChecked(false); }


        vib_Parent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(vib_Switch.isChecked()) {
                    vib_Switch.setChecked(false);
                    vibCnt = "0";
                } else {
                    vib_Switch.setChecked(true);
                    vibCnt = "1";
                }
            }
        });

        //다시 울림 설정////////////////////////////////////////////
        repeat_Parent = findViewById(R.id.repeat_Parent);
        repeat_Time = findViewById(R.id.repeat_Time);
        if(alarm_Repeat.toString().equals("0")) {
            repeat_Time.setText("사용 안함");
        } else {
            repeat_Time.setText(alarm_Repeat + "분");
            for (int i = 0; i < snoozeTime.length; i++) {
                if(TextUtils.equals(snoozeTime[i], (alarm_Repeat + "분"))) {
                    snoozeCnt = i;
                }
            }
        }



        repeat_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSnooze(alarm_Repeat, snoozeCnt);
            }
        });

        //확인 버튼////////////////////////////////////////////
        confirm_Parent = findViewById(R.id.confirm_Parent);
        confirm_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dto = new AlarmDTO();
                dto.setAlarm_Email(alarm_Email);
                dto.setAlarm_Id(alarm_Id);
                if (setName_Name.getText().length() < 1) {
                    dto.setAlarm_Title("복용 알람");
                } else {
                    dto.setAlarm_Title("" + setName_Name.getText());
                }
                dto.setAlarm_Sunday(dayCnt[0]);
                dto.setAlarm_Monday(dayCnt[1]);
                dto.setAlarm_Tuesday(dayCnt[2]);
                dto.setAlarm_Wednesday(dayCnt[3]);
                dto.setAlarm_Thursday(dayCnt[4]);
                dto.setAlarm_Friday(dayCnt[5]);
                dto.setAlarm_Saturday(dayCnt[6]);
                dto.setAlarm_Times(timesCnt);
                dto.setAlarm_Ringtime1_Hour(timeTexts[0][0]);
                dto.setAlarm_Ringtime1_Minute(timeTexts[0][1]);
                dto.setAlarm_Ringtime2_Hour(timeTexts[1][0]);
                dto.setAlarm_Ringtime2_Minute(timeTexts[1][1]);
                dto.setAlarm_Ringtime3_Hour(timeTexts[2][0]);
                dto.setAlarm_Ringtime3_Minute(timeTexts[2][1]);
                dto.setAlarm_Volume(volume[0]);
                dto.setAlarm_Bell(bell_Name.getText());
                dto.setAlarm_Vib(vibCnt);
                dto.setAlarm_Repeat(String.valueOf(snoozeVal));

                alarm_Bundle = new Bundle();
                alarm_Bundle.putSerializable("alarmDTO", dto);

                Log.d(TAG, "onClick: " + dto.getAlarm_Email() + " / " + dto.getAlarm_Id()  + " / " + dto.getAlarm_Title() + " / " + dto.getAlarm_Sunday() + " / " + dto.getAlarm_Monday() + " / " + dto.getAlarm_Tuesday() + " / " +
                        dto.getAlarm_Wednesday() + " / " + dto.getAlarm_Thursday() + " / " + dto.getAlarm_Friday() + " / " + dto.getAlarm_Saturday() + " / " + dto.getAlarm_Times() + " / " +
                        dto.getAlarm_Ringtime1_Hour() + " / " + dto.getAlarm_Ringtime1_Minute() + " / " + dto.getAlarm_Ringtime2_Hour() + " / " + dto.getAlarm_Ringtime2_Minute() + " / " + dto.getAlarm_Ringtime3_Hour() + " / " +
                        dto.getAlarm_Ringtime3_Minute() + " / " + dto.getAlarm_Volume() + " / " + dto.getAlarm_Bell() + " / " + dto.getAlarm_Vib() + " / " + dto.getAlarm_Repeat());

                AlarmUpdate alarmUpdate = new AlarmUpdate(
                        dto.getAlarm_Email(), dto.getAlarm_Id(),dto.getAlarm_Title(), dto.getAlarm_Sunday(), dto.getAlarm_Monday(), dto.getAlarm_Tuesday(),
                        dto.getAlarm_Wednesday(), dto.getAlarm_Thursday(), dto.getAlarm_Friday(), dto.getAlarm_Saturday(), dto.getAlarm_Times(),
                        dto.getAlarm_Ringtime1_Hour(), dto.getAlarm_Ringtime1_Minute(), dto.getAlarm_Ringtime2_Hour(), dto.getAlarm_Ringtime2_Minute(), dto.getAlarm_Ringtime3_Hour(),
                        dto.getAlarm_Ringtime3_Minute(), dto.getAlarm_Volume(), dto.getAlarm_Bell(), dto.getAlarm_Vib(), dto.getAlarm_Repeat());

                alarmUpdate.execute();
                finish();
            }
        });

        //취소 버튼////////////////////////////////////////////
        cancel_Parent = findViewById(R.id.cancel_Parent);
        cancel_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //횟수 설정 버튼 누르면 작동하는 메서드
    public void onToggle(View view) {
        ((RadioGroup) view.getParent()).check(view.getId());

        if(times_one.isChecked()) {
            timesCnt = "1";
            setTime_1.setEnabled(true);
            setTime_2.setEnabled(false);
            setTime_3.setEnabled(false);
        } else if(times_two.isChecked()) {
            timesCnt = "2";
            setTime_1.setEnabled(true);
            setTime_2.setEnabled(true);
            setTime_3.setEnabled(false);
        } else if(times_three.isChecked()) {
            timesCnt = "3";
            setTime_1.setEnabled(true);
            setTime_2.setEnabled(true);
            setTime_3.setEnabled(true);
        } else {
            timesCnt = "0";
            setTime_1.setEnabled(false);
            setTime_2.setEnabled(false);
            setTime_3.setEnabled(false);
        }
    }

    //횟수 버튼 하나만 토글되게 설정하는 리스너
    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };

    //토글 버튼이 온이면 1, 오프면 0을 반환하는 메서드
    public CharSequence toggleCheck(ToggleButton tb) {
        CharSequence chkCnt = "0";
        if(tb.isChecked()) {
            chkCnt = "1";
        } else {
            chkCnt = "0";
        }
        return chkCnt;
    }

    //시간 설정 리스너 배열
    private TimePickerDialog.OnTimeSetListener[] timeSetListeners = {
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String hourString = String.valueOf(hour);
                    String minuteString = String.valueOf(minute);

                    if (hour < 10) {
                        hourString = "0" + hour;
                    }
                    if (minute < 10) {
                        minuteString = "0" + minute;
                    }

                    setTime_1.setText(hourString + ":" + minuteString);
                }
            }, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            String hourString = String.valueOf(hour);
            String minuteString = String.valueOf(minute);

            if (hour < 10) {
                hourString = "0" + hour;
            }
            if (minute < 10) {
                minuteString = "0" + minute;
            }

            setTime_2.setText(hourString + ":" + minuteString);
        }
    }, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            String hourString = String.valueOf(hour);
            String minuteString = String.valueOf(minute);

            if (hour < 10) {
                hourString = "0" + hour;
            }
            if (minute < 10) {
                minuteString = "0" + minute;
            }

            setTime_3.setText(hourString + ":" + minuteString);
        }
    }
    };

    private void setSnooze(final CharSequence alarm_Repeat, final int snoozeCnt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("다시 울림");
        builder.setIcon(R.drawable.ic_snooze_white_48dp);
        builder.setSingleChoiceItems(snoozeTime, snoozeCnt, new DialogInterface.OnClickListener() { //숫자:선택된 번째
            @Override
            public void onClick(DialogInterface dialog, int index) {
                repeat_Time.setText(snoozeTime[index]);
                if(snoozeCnt == 0) {
                    snoozeVal = 0;
                } else {
                    String snoozeTemp = snoozeTime[index].toString();
                    snoozeVal = Integer.parseInt(snoozeTemp.substring(0, snoozeTemp.length() - 1));
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK) {
            uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                ringTonePath = uri.toString();
                ringToneName = RingtoneManager.getRingtone(getApplicationContext(), uri).getTitle(getApplicationContext());
                bell_Name.setText(ringToneName);
            } else {
                bell_Name.setText("무음");
            }
        }
    }

    public void getBundle(Bundle bundle) {
        this.alarm_Bundle = bundle;
    }
}