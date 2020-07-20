package com.example.auto_medic;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    //객체 선언
    TextView mainF_Title;
    ImageButton mainF_Search, mainF_Map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //XML, java 연결
        //XML이 메인에 직접 붙으면 true, 프래그먼트에 붙으면 false
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        //객체 초기화
        mainF_Title = rootView.findViewById(R.id.mainF_Title);
        mainF_Search = rootView.findViewById(R.id.mainF_Search);
        mainF_Map = rootView.findViewById(R.id.mainF_Map);

        //약 검색 버튼
        mainF_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchMedicineActivity.class);
                startActivity(intent);
            }
        });

        //지도 버튼
        mainF_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}