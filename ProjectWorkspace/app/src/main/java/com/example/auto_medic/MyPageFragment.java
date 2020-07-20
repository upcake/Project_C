package com.example.auto_medic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.auto_medic.ATask.InfoSelect;
import com.example.auto_medic.Dto.InfoDTO;

import java.io.Serializable;

import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;

public class MyPageFragment extends Fragment {
    private static final String TAG = "main:MyPageFragment";

    //객체 선언
    ImageView myPageF_Profile;
    Button myPageF_MyInfo, myPageF_DeviceSetting, myPageF_Calendar, myPageF_Emergency, myPageF_Logout;
    TextView myPageF_Nickname, myPageF_Email;
    InfoDTO infoDTO;

    String info_email;
    InfoSelect infoSelect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);

        info_email = "aaaaa@naver.com";
        try {
            if(isNetworkConnected(getContext()) == true) {
                infoSelect = new InfoSelect(info_email);
                infoDTO = infoSelect.execute().get();

                Log.d(TAG, "onCreateView: infoDTO :" + infoDTO.getInfo_nickname());
            } else {
                Toast.makeText(getContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //객체 초기화
        myPageF_Profile = rootView.findViewById(R.id.myPageF_Profile);
        myPageF_Nickname = rootView.findViewById(R.id.myPageF_Nickname);
        myPageF_Email = rootView.findViewById(R.id.myPageF_Email);
        myPageF_MyInfo = rootView.findViewById(R.id.myPageF_MyInfo);
        myPageF_DeviceSetting = rootView.findViewById(R.id.myPageF_DeviceSetting);
        myPageF_Calendar = rootView.findViewById(R.id.myPageF_Calendar);
        myPageF_Emergency = rootView.findViewById(R.id.myPageF_Emergency);
        myPageF_Logout = rootView.findViewById(R.id.myPageF_Logout);

        //받은 정보로 마이 페이지 메뉴 바꾸기
        myPageF_Nickname.setText(infoDTO.getInfo_nickname());
        myPageF_Email.setText(infoDTO.getInfo_email());

        //회원 정보 변경 벼튼
        myPageF_MyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected(getContext()) == true){
                    Log.d("sub1:update1", infoDTO.getInfo_email());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("infoDTO", infoDTO);

                    Intent intent = new Intent(getContext(), MyInfoActivity.class);
                    intent.putExtra("bundle", bundle);

                    startActivityForResult(intent, 10005);
                } else {
                    Toast.makeText(view.getContext(), "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}