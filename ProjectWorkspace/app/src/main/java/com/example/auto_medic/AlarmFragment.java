package com.example.auto_medic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_medic.ATask.AlarmSelect;
import com.example.auto_medic.Adapter.AlarmRecyclerViewAdapter;
import com.example.auto_medic.Dto.AlarmDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;

public class AlarmFragment extends Fragment {
    //객체 선언
    TextView alarmF_NextAlarm_Title, alarmF_NextAlarm, alarmF_SetBluetooth;
    ImageButton alarmF_BlueOnOff;
    int blueCnt;
    FloatingActionButton alarmF_Add, alarmF_SetBluetooth_Button, alarmF_AddAlarm;
    int rotateCnt;

    //다음 알람 시간
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    Date today;
    long curTimeMillis;

    //리사이클러 뷰
    AlarmSelect alarmSelect;
    public static ArrayList<AlarmDTO> dtoArrayList;
    RecyclerView alarmF_RecyclerView;
    AlarmRecyclerViewAdapter adapter;
    public static AlarmDTO selItem = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //XML, java 연결
        //XML이 메인에 직접 붙으면 true, 프래그먼트에 붙으면 false
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);

        //객체 초기화
        alarmF_NextAlarm_Title = rootView.findViewById(R.id.alarmF_NextAlarm_Title);
        alarmF_NextAlarm = rootView.findViewById(R.id.alarmF_NextAlarm);
        alarmF_SetBluetooth = rootView.findViewById(R.id.alarmF_SetBluetooth);
        alarmF_BlueOnOff = rootView.findViewById(R.id.alarmF_BlueOnOff);
        alarmF_Add = rootView.findViewById(R.id.alarmF_Add);
        alarmF_SetBluetooth_Button = rootView.findViewById(R.id.alarmF_SetBluetooth_Button);
        alarmF_AddAlarm = rootView.findViewById(R.id.alarmF_AddAlarm);

        //다음 알람 시간
        Date today = new Date();
        curTimeMillis = System.currentTimeMillis();
        final String curTime = sdf.format(curTimeMillis);


        alarmF_NextAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), curTime, Toast.LENGTH_SHORT).show();
            }
        });


        //블루투스 알람 설정 클릭 시
        blueCnt = 1; //0 : 꺼진 상태, 1 : 켜진 상태
        alarmF_SetBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blueCnt == 1) {
                    alarmF_BlueOnOff.setImageResource(R.drawable.bluetooth_off);
                    alarmF_BlueOnOff.setBackgroundColor(Color.argb(37,00,00,00));
                    blueCnt = 0;
                } else if(blueCnt == 0) {
                    alarmF_BlueOnOff.setImageResource(R.drawable.bluetooth_on);
                    alarmF_BlueOnOff.setBackgroundColor(Color.rgb(00,68,145));
                    blueCnt = 1;
                }
            }
        });

        //블루투스 On/Off 클릭 시 아이콘 바꾸기
        alarmF_BlueOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blueCnt == 1) {
                    alarmF_BlueOnOff.setImageResource(R.drawable.bluetooth_off);
                    alarmF_BlueOnOff.setBackgroundColor(Color.argb(37,00,00,00));
                    blueCnt = 0;
                } else if(blueCnt == 0) {
                    alarmF_BlueOnOff.setImageResource(R.drawable.bluetooth_on);
                    alarmF_BlueOnOff.setBackgroundColor(Color.rgb(00,68,145));
                    blueCnt = 1;
                }
            }
        });

        //플러스 버튼 클릭 시
        rotateCnt = 0;
        alarmF_AddAlarm.setVisibility(alarmF_AddAlarm.GONE);
        alarmF_SetBluetooth_Button.setVisibility(alarmF_SetBluetooth_Button.GONE);

        alarmF_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rotateCnt == 0) {
                    alarmF_Add.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
                    alarmF_Add.bringToFront();
                    alarmF_AddAlarm.setVisibility(alarmF_AddAlarm.VISIBLE);
                    alarmF_SetBluetooth_Button.setVisibility(alarmF_SetBluetooth_Button.VISIBLE);
                    alarmF_AddAlarm.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.appear));
                    alarmF_SetBluetooth_Button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.appear2));
                    rotateCnt = 1;
                } else if(rotateCnt == 1) {
                    alarmF_Add.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_reverse));
                    alarmF_Add.bringToFront();
                    alarmF_SetBluetooth_Button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.disappear2));
                    alarmF_SetBluetooth_Button.setVisibility(alarmF_SetBluetooth_Button.GONE);
                    alarmF_AddAlarm.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.disappear));
                    alarmF_AddAlarm.setVisibility(alarmF_AddAlarm.GONE);
                    rotateCnt = 0;
                }
            }
        });

        //알람 추가 버튼 클릭 시
        alarmF_AddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
                startActivityForResult(intent, 1004);
            }
        });

        //블루투스 설정 버튼 클릭 시
        alarmF_SetBluetooth_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BluetoothActivity.class);
                startActivity(intent);
            }
        });

        //리사이클러 뷰 시작
         dtoArrayList = new ArrayList();
        adapter = new AlarmRecyclerViewAdapter(getContext(), dtoArrayList);
        alarmF_RecyclerView = rootView.findViewById(R.id.alarmF_RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        alarmF_RecyclerView.setLayoutManager(layoutManager);

        alarmF_RecyclerView.setAdapter(adapter);

        if(isNetworkConnected(getContext()) == true) {
            alarmSelect = new AlarmSelect(dtoArrayList, adapter);
            alarmSelect.execute();
        } else {
            Toast.makeText(getContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(isNetworkConnected(getContext()) == true) {
            alarmSelect = new AlarmSelect(dtoArrayList, adapter);
            alarmSelect.execute();
        } else {
            Toast.makeText(getContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}

