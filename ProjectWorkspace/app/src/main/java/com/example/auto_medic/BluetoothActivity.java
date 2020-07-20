package com.example.auto_medic;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.auto_medic.Dto.AlarmDTO;
import com.example.auto_medic.Dto.BlueAlarmDTO;

public class BluetoothActivity extends AppCompatActivity {
    //객체 선언
    //볼륨 조절
    ConstraintLayout vol_Parent;
    SeekBar vol_SeekBar;

    //벨소리 조절
    ConstraintLayout bell_Parent;
    TextView bell_Name;
    String ringToneName;
    String ringTonePath;
    Intent intent;
    Uri uri;

    //진동 조절
    ConstraintLayout vib_Parent;
    Switch vib_Switch;

    //다시 울림 설정
    ConstraintLayout repeat_Parent;
    TextView repeat_Time;
    final CharSequence [] snoozeTime ={"사용 안함", "1분","5분","10분","15분","20분", "25분", "30분"};
    int snoozeCnt = 0;

    //확인 버튼
    ConstraintLayout confirm_Parent;
    BlueAlarmDTO dto;
    Bundle blueAlarm_Bundle;

    //취소 버튼
    ConstraintLayout cancel_Parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        //볼륨 설정////////////////////////////////////////////
        vol_Parent = findViewById(R.id.vol_Parent);
        vol_SeekBar = findViewById(R.id.vol_SeekBar);
        final int[] volume = {100};

        vol_Parent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (volume[0] == 0) {
                    vol_SeekBar.setProgress(100, true);
                } else {
                    vol_SeekBar.setProgress(0, true);
                }
            }
        });

        vol_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                volume[0] = progress;
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

        bell_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "벨소리 선택");
                startActivityForResult(intent, 101);
            }
        });

        //진동 설정////////////////////////////////////////////
        vib_Parent = findViewById(R.id.vib_Parent);
        vib_Switch = findViewById(R.id.vib_Switch);

        vib_Parent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (vib_Switch.isChecked()) {
                    vib_Switch.setChecked(false);
                } else {
                    vib_Switch.setChecked(true);
                }
            }
        });

        //다시 울림 설정////////////////////////////////////////////
        repeat_Parent = findViewById(R.id.repeat_Parent);
        repeat_Time = findViewById(R.id.repeat_Time);

        repeat_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSnooze();
            }
        });

        //확인 버튼////////////////////////////////////////////
        confirm_Parent = findViewById(R.id.confirm_Parent);
        confirm_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dto = new BlueAlarmDTO();
                dto.setAlarm_Volume(volume[0]);
                dto.setAlarm_Bell(bell_Name.getText());
                dto.setAlarm_Repeat(repeat_Time.getText());

                blueAlarm_Bundle = new Bundle();
                blueAlarm_Bundle.putSerializable("alarmDTO", dto);

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

    private void setSnooze() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("다시 울림");
        builder.setIcon(R.drawable.ic_snooze_white_48dp);
        builder.setSingleChoiceItems(snoozeTime, snoozeCnt, new DialogInterface.OnClickListener() { //숫자:선택된 번째
            @Override
            public void onClick(DialogInterface dialog, int index) {
                repeat_Time.setText(snoozeTime[index]);
                snoozeCnt = index;
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
        this.blueAlarm_Bundle = bundle;
    }
}
