package com.example.auto_medic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.auto_medic.ATask.EmerListAsync;
import com.example.auto_medic.Dto.EmergencyDTO;

import java.util.ArrayList;

import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;


public class EmergencyAddressActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EmergencyAdapter adapter;
    Button adbtn;
    ArrayList<EmergencyDTO> items;
    EmerListAsync emerListAsync;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencyaddress);
        // 리사이클러 뷰 시작
        items=new ArrayList<>();
        adapter=new EmergencyAdapter(items, this );
        adbtn=findViewById(R.id.adbtn);
        recyclerView=findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

            if(isNetworkConnected(this) == true){
            emerListAsync = new EmerListAsync(items, adapter, progressDialog);
            emerListAsync.execute();

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


        adbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EmergencyContactActivity.class);
                startActivity(intent);
            }
        });

    }

   // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Sub1", "onNewIntent() 호출됨");

        //listView 갱신 안 시키면 오류남 갱신 시킨 것을 알려줌
        adapter.notifyDataSetChanged();
        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeall();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            emerListAsync = new EmerListAsync(items, adapter, progressDialog);
            emerListAsync.execute();
        }
    }



}
