package com.example.auto_medic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.auto_medic.Common.CommonMethod.isNetworkConnected;

public class MainActivity extends AppCompatActivity {
    //프래그먼트 객체 선언
    AlarmFragment alarmFragment;
    MainFragment mainFragment;
    MyPageFragment myPageFragment;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뷰페이저 객체 초기화
        viewPager = findViewById(R.id.viewPager);

        //페이저 기능 추가
        //setOffscreenPageLimit() : 좌우를 포함해해 미리 생성해 둘 페이지 수
        viewPager.setOffscreenPageLimit(3);

        //페이저 어댑터 객체 초기화
        //MyPagerAdapter는 프래그먼트매니저를 매개 변수로 받는다.
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());

        //프래그먼트 객체 초기화
        alarmFragment = new AlarmFragment();
        mainFragment = new MainFragment();
        myPageFragment = new MyPageFragment();

        //어댑터에 프래그먼트 추가
        viewPagerAdapter.addItem(alarmFragment);
        viewPagerAdapter.addItem(mainFragment);
        viewPagerAdapter.addItem(myPageFragment);

        viewPager.setAdapter(viewPagerAdapter);


        //화면에 메인 프래그먼트 출력
        //getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, mainFragment).commit();
        viewPager.setCurrentItem(1);

        //바텀 네비게이션 객체 초기화
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab2);

        //화면 슬라이드로 변경 시 바텀 네비게이션 위치 변경
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.tab1);
                } else if (position == 1) {
                    bottomNavigationView.setSelectedItemId(R.id.tab2);
                } else if (position == 2) {
                    bottomNavigationView.setSelectedItemId(R.id.tab3);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //바텀 네비게이션에 이벤트 추가
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //바텀 네비게이션 버튼에 눌렀을때 화면 바뀌는 기능 추가
                //item.getItemId() 아이템의 아이디를 바로 가져옴
                switch (item.getItemId()) {
                    case R.id.tab1:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, alarmFragment).commit();
                        viewPager.setCurrentItem(0);
                        return true; //return true 밑으로는 작동이 되지 않는다.

                    case R.id.tab2:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, mainFragment).commit();
                        viewPager.setCurrentItem(1);
                        return true;

                    case R.id.tab3:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, myPageFragment).commit();
                        viewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        //MyPagerAdapter 클래스를 만들고 Alt + Enter로 implement 하고 생성자를 만든다
        public MyViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        //Fragment를 넣을 리스트 초기화
        ArrayList<Fragment> items = new ArrayList<>();

        //리스트에 프래그먼트를 추가하는 메서드를 만든다
        public void addItem(Fragment item) {
            items.add(item);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            //items의 프래그먼트 인덱스를 받아서 리턴하게끔 설정
            return items.get(position);
        }

        //items에 들어있는 원소의 갯수를 반환
        @Override
        public int getCount() {
            return items.size();
        }

        //CharSequence : 어떤 글자를 넘길 것인가
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "페이지" + (position + 1);
        }
    }
}