package com.example.auto_medic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //프래그먼트 객체 선언
    AlarmFragment alarmFragment;
    MainFragment mainFragment;
    MyPageFragment myPageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //프래그먼트 객체 초기화
        alarmFragment = new AlarmFragment();
        mainFragment = new MainFragment();
        myPageFragment = new MyPageFragment();


        //화면에 메인 프래그먼트 출력
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();

        //바텀 네비게이션 객체 초기화
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab2);

        //바텀 네비게이션에 이벤트 추가
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //바텀 네비게이션 버튼에 눌렀을때 화면 바뀌는 기능 추가
                //item.getItemId() 아이템의 아이디를 바로 가져옴
                switch (item.getItemId()) {
                    case R.id.tab1:
                        Toast.makeText(MainActivity.this, "첫 번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alarmFragment).commit();
                        return true; //return true 밑으로는 작동이 되지 않는다.

                    case R.id.tab2:
                        Toast.makeText(MainActivity.this, "두 번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
                        return true;

                    case R.id.tab3:
                        Toast.makeText(MainActivity.this, "세 번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myPageFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
