package com.example.auto_medic;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyPageFragment extends Fragment {
    //객체 선언
    ImageView myPageF_Profile;
    Button myPageF_MyInfo, myPageF_DeviceSetting, myPageF_Calendar, myPageF_Emergency, myPageF_Logout;
    TextView myPageF_Nickname, myPageF_Email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);

        //객체 초기화
        myPageF_Profile = rootView.findViewById(R.id.myPageF_Profile);
        myPageF_Nickname = rootView.findViewById(R.id.myPageF_Nickname);
        myPageF_Email = rootView.findViewById(R.id.myPageF_Email);
        myPageF_MyInfo = rootView.findViewById(R.id.myPageF_MyInfo);
        myPageF_DeviceSetting = rootView.findViewById(R.id.myPageF_DeviceSetting);
        myPageF_Calendar = rootView.findViewById(R.id.myPageF_Calendar);
        myPageF_Emergency = rootView.findViewById(R.id.myPageF_Emergency);
        myPageF_Logout = rootView.findViewById(R.id.myPageF_Logout);

        //회원 정보 변경 벼튼
        myPageF_MyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                startActivity(intent);
            }
        });

        //기기 설정 버튼
        myPageF_DeviceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeviceSettingActivity.class);
                startActivity(intent);
            }
        });

        //복용 캘린더 버튼
        myPageF_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        //비상 연락망 버튼
        myPageF_Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EmergencyContactActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃 버튼
        myPageF_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}