package com.example.auto_medic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MapActivity extends AppCompatActivity implements MapView.POIItemEventListener {
    Button nowPos,nearDrug,nowHospital;
    MapView mapView;
    TextView data;

    ArrayList<CoronaDTO> dtos;
    ArrayList<DrugDTO> drugs;
    ArrayList<hospitalDTO> hospitals;

    ArrayList<hospitalDTO> nearhospitals;
    ArrayList<CoronaDTO> nearcoronas;
    ArrayList<DrugDTO> neardrugs;

    private static final String TAG = "MapActivity";
    //박형규가 만든거
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        nowPos = findViewById(R.id.nowLocation);
        nearDrug = findViewById(R.id.nowMedicine);
        nowHospital = findViewById(R.id.nowHospital);
        mapView = new MapView(this);
       // data = findViewById(R.id.);
        MapPOIItem map = new MapPOIItem();
        //map.setShowCalloutBalloonOnTouch(false);

        mapView.setPOIItemEventListener(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
      /*  try {
            drug();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (BiffException ex) {
            ex.printStackTrace();
        }*/

        dtos =  method();
        hospitals = hospital();
        Log.d(TAG, "onCreate: 온크리에이트 크기"+dtos.size());

        try {
           drugs= drug();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "onCreate: 엑셀파싱");

        //주변 코로나 검사소 찾기
        nowPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearcoronas = radius(dtos);
            }
        });

        //주변 약국 찾기
        nearDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 현재약국 클릭됨");
                neardrugs = drugradius(drugs);
            }
        });

        //주변병원
        nowHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 현재병원 클릭됨");
               nearhospitals = hospitalradius(hospitals);
            }
        });

    }

    //주변 병원 반경찍기
    public ArrayList<hospitalDTO> hospitalradius(ArrayList<hospitalDTO> hospitals) {
        mapView.removeAllCircles();
        mapView.removeAllPOIItems();
        Log.d(TAG, "최초 메소드 진입 리스트 크기: "+hospitals.size());
        Double nowlatitude = null;  //현재위치 위도
        Double nowlongtitude = null;    //현재위치 경도
        //①현재 좌표를 가져오고
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;   //10초 마다 갱신
        float minDistance = 0;  //위치변화가 없더라도(0) 갱신한다

        mapView.removeAllCircles();
        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener );  //위성
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener ); //기지국
            Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            nowlatitude = lastLocation.getLatitude();
            nowlongtitude = lastLocation.getLongitude();


            // 중심점 변경
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), true);

            //현재 위치 마커 표시
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("현재위치");
            marker.setTag(0);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.


            // 줌 레벨 변경
            mapView.setZoomLevel(4, true);
            mapView.addPOIItem(marker);

            //반경표시
            MapCircle circle1 = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), // center
                    1000, // radius
                    Color.argb(128, 255, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);

        }catch (SecurityException e){
            e.getMessage();

        }
        Double coronaLatitude ;
        Double coronaLongtitude;
        double distanceKiloMeter = 0;

        ArrayList<hospitalDTO> nearArea = new ArrayList<>();
        Log.d(TAG, "주변약국 진입 전 광주전체약국 갯수: "+hospitals.size());
        for(int i=0;i<hospitals.size();i++){
            coronaLatitude = hospitals.get(i).latitude;
            coronaLongtitude = hospitals.get(i).longtitude;
            Log.d(TAG, "광주전체약국의 이름?: "+dtos.get(i).getName());
            distanceKiloMeter =
                    distance(nowlatitude, nowlongtitude, coronaLatitude, coronaLongtitude, "meter");
            if (distanceKiloMeter <= 1000) {
                nearArea.add(hospitals.get(i));

            }
        }
        MapPOIItem marker;

        //범위내에 있는거만 마커찍기!!!
        for(int i=1;i<nearArea.size();i++){
            Log.d(TAG, "radius: "+i);

            Log.d(TAG, "radius: 마커찍는중"+i);

            marker = new MapPOIItem();
            marker.setItemName(nearArea.get(i).getName());
            marker.setTag(i);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nearArea.get(i).latitude, nearArea.get(i).longtitude));
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setCustomImageResourceId(R.drawable.hospital_btn);
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomSelectedImageResourceId(R.drawable.hospital_btn_ed);

            mapView.addPOIItem(marker);
        }

        //③현재 좌표와 병원들의 좌표를 거리(단위:미터)로 변환해서 반경 5000m안에 있는 것들만 마커 찍어준다.
        return nearArea;

    }

    //주변 병원 엑셀에서 가져오기
    public ArrayList<hospitalDTO> hospital(){
        ArrayList<hospitalDTO> dtos = new ArrayList<>();
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("hospital_in_gj.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getRows();

                    StringBuilder sb;
                    Log.d(TAG, "method: 반복문 전");
                    Log.d(TAG, "method: 칼럼토탈"+colTotal);
                    Log.d(TAG, "method: 로우토탈"+rowTotal);

                    for(int row=rowIndexStart;row<rowTotal;row++) {


                        String name = sheet.getCell(0,row).getContents();
                        Log.d(TAG, "hospital: "+sheet.getCell(6,row).getContents());
                        Log.d(TAG, "hospital: "+sheet.getCell(5,row).getContents());

                        String finalLat = sheet.getCell(6,row).getContents().substring(0,2)+"."+sheet.getCell(6,row).getContents().substring(2);
                        String finalLong = sheet.getCell(5,row).getContents().substring(0,3)+"."+sheet.getCell(5,row).getContents().substring(3);

                        Double longtitude = Double.parseDouble(finalLong);
                        Double latitude = Double.parseDouble(finalLat);


                        Log.d(TAG, "이름: "+name);
                        Log.d(TAG, "경도: "+longtitude);
                        Log.d(TAG, "위도: "+latitude);

                        dtos.add(new hospitalDTO(sheet.getCell(2,row).getContents(),sheet.getCell(3,row).getContents(),sheet.getCell(4,row).getContents(),longtitude,latitude));


                        Log.d(TAG, "method: "+dtos.size());
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "method: 익셉션1?");
        } catch (BiffException e) {
            e.printStackTrace();
            Log.d(TAG, "method: 익셉션2?");
        }

        return dtos;
    }



    //주변 코로나검사소 반경
    public ArrayList<CoronaDTO> radius(ArrayList<CoronaDTO> dtos){
        ArrayList<CoronaDTO> nearArea;
        mapView.removeAllCircles();
        mapView.removeAllPOIItems();
        Double nowlatitude = null;  //현재위치 위도
        Double nowlongtitude = null;    //현재위치 경도
        //①현재 좌표를 가져오고
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;   //10초 마다 갱신
        float minDistance = 0;  //위치변화가 없더라도(0) 갱신한다

        mapView.removeAllCircles();
        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener );  //위성
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener ); //기지국
            Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            nowlatitude = lastLocation.getLatitude();
            nowlongtitude = lastLocation.getLongitude();


            // 중심점 변경
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), true);

            //현재 위치 마커 표시
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("현재위치");
            marker.setTag(0);

            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            // 줌 레벨 변경
            mapView.setZoomLevel(6, true);

            mapView.addPOIItem(marker);

            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude));

            //반경표시
            MapCircle circle1 = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), // center
                    5000, // radius
                    Color.argb(128, 255, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);

        }catch (SecurityException e){
            e.getMessage();

        }
        Double coronaLatitude ;
        Double coronaLongtitude;
        double distanceKiloMeter = 0;

        //②엑셀에서 병원들의 좌표를 가져오고
            ArrayList<CoronaDTO> list = method();

             nearArea = new ArrayList<>();

            for(int i=0;i<list.size();i++){
                coronaLatitude = dtos.get(i).getPhone();
                coronaLongtitude = dtos.get(i).getAddr();
                Log.d(TAG, "갑자기?: "+nowlatitude);
                distanceKiloMeter =
                        distance(nowlatitude, nowlongtitude, coronaLatitude, coronaLongtitude, "meter");
                if (distanceKiloMeter <= 5000) {
                    nearArea.add(dtos.get(i));

                }
            }

            //범위내에 있는거만 마커찍기!!!
            for(int i=1;i<nearArea.size();i++){
                Log.d(TAG, "radius: "+i);

                Log.d(TAG, "radius: 마커찍는중"+i);

                        MapPOIItem marker = new MapPOIItem();
                        marker.setItemName(nearArea.get(i).getName());
                        marker.setTag(i);
                      marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nearArea.get(i).getPhone(), nearArea.get(i).getAddr()));
                marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setCustomImageResourceId(R.drawable.corona_btn);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                marker.setCustomSelectedImageResourceId(R.drawable.corona_btn_ed);

                        mapView.addPOIItem(marker);

            }
        //③현재 좌표와 병원들의 좌표를 거리(단위:미터)로 변환해서 반경 5000m안에 있는 것들만 마커 찍어준다.

        return nearArea;
    }

    //주변 약국 반경
    public ArrayList<DrugDTO> drugradius(ArrayList<DrugDTO> dtos){
        ArrayList<DrugDTO> nearArea;
        mapView.removeAllCircles();
        mapView.removeAllPOIItems();
        Log.d(TAG, "최초 메소드 진입 리스트 크기: "+dtos.size());
        Double nowlatitude = null;  //현재위치 위도
        Double nowlongtitude = null;    //현재위치 경도
        //①현재 좌표를 가져오고
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;   //10초 마다 갱신
        float minDistance = 0;  //위치변화가 없더라도(0) 갱신한다

        mapView.removeAllCircles();
        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener );  //위성
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener ); //기지국
            Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            nowlatitude = lastLocation.getLatitude();
            nowlongtitude = lastLocation.getLongitude();


            // 중심점 변경
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), true);

            //현재 위치 마커 표시
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("현재위치");
            marker.setTag(0);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            // 줌 레벨 변경
            mapView.setZoomLevel(6, true);

            mapView.addPOIItem(marker);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude));

            //반경표시
            MapCircle circle1 = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), // center
                    1000, // radius
                    Color.argb(128, 255, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);

        }catch (SecurityException e){
            e.getMessage();

        }
        Double coronaLatitude ;
        Double coronaLongtitude;
        double distanceKiloMeter = 0;

        nearArea = new ArrayList<>();
        Log.d(TAG, "주변약국 진입 전 광주전체약국 갯수: "+dtos.size());
        for(int i=0;i<dtos.size();i++){
            coronaLatitude = dtos.get(i).getLat();
            coronaLongtitude = dtos.get(i).getLon();
            Log.d(TAG, "광주전체약국의 이름?: "+dtos.get(i).getName());
            distanceKiloMeter =
                    distance(nowlatitude, nowlongtitude, coronaLatitude, coronaLongtitude, "meter");
            if (distanceKiloMeter <= 1000) {
                nearArea.add(dtos.get(i));

            }
        }


        //범위내에 있는거만 마커찍기!!!
        for(int i=1;i<nearArea.size();i++){
            Log.d(TAG, "radius: "+i);

            Log.d(TAG, "radius: 마커찍는중"+i);

            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(nearArea.get(i).getName());
            marker.setTag(i);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nearArea.get(i).lat, nearArea.get(i).lon));
            Log.d(TAG, "인덱스 "+i);
            Log.d(TAG, "범위내 이름 "+nearArea.get(i).getName());
            Log.d(TAG, "범위내 lat: "+nearArea.get(i).getLat());
            Log.d(TAG, "범위내 long: "+nearArea.get(i).getLon());
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setCustomImageResourceId(R.drawable.medicine_btn);
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomSelectedImageResourceId(R.drawable.medicine_btn_ed);

            mapView.addPOIItem(marker);

        }
        //③현재 좌표와 병원들의 좌표를 거리(단위:미터)로 변환해서 반경 5000m안에 있는 것들만 마커 찍어준다.

        return  nearArea;
    }
    //좌표간 거리 구하는 메소드
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    //엑셀에서 정보를 가져와 list형식으로 반환하는 메소드
    public ArrayList<CoronaDTO> method(){

        ArrayList<CoronaDTO> dtos = new ArrayList<>();
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("finalresult.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getRows();

                    StringBuilder sb;
                    Log.d(TAG, "method: 반복문 전");
                    Log.d(TAG, "method: 칼럼토탈"+colTotal);
                    Log.d(TAG, "method: 로우토탈"+rowTotal);

                    for(int row=rowIndexStart;row<rowTotal;row++) {

                        DecimalFormat formLong = new DecimalFormat("####,#######");
                        DecimalFormat formLat = new DecimalFormat("##,########");   //위도
                        String name = sheet.getCell(0,row).getContents();


                        String strLongtitude = formLong.format(Double.parseDouble(sheet.getCell(3,row).getContents())).replace(",",".");
                        String strLatitude = formLat.format(Double.parseDouble(sheet.getCell(4,row).getContents())).replaceAll(",",".");
                        String addr = sheet.getCell(1,row).getContents() ;
                        String tel = sheet.getCell(2,row).getContents();
                        Double longtitude = Double.parseDouble(strLongtitude);
                        Double latitude = Double.parseDouble(strLatitude);


                        Log.d(TAG, "이름: "+name);
                        Log.d(TAG, "경도: "+longtitude);
                        Log.d(TAG, "위도: "+latitude);

                        dtos.add(new CoronaDTO(name, longtitude, latitude,addr,tel));

                        Log.d(TAG, "method: "+dtos.size());
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "method: 익셉션1?");
        } catch (BiffException e) {
            e.printStackTrace();
            Log.d(TAG, "method: 익셉션2?");
        }
        
        return dtos;
    }

    //엑셀에서 정보를 가져와 list형식으로 반환하는 메소드
    public ArrayList<DrugDTO> drug() throws IOException, BiffException {

        ArrayList<DrugDTO> dtos = new ArrayList<>();
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("drugstore.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getRows();

                    Log.d(TAG, "drug: 약국정보조회");
                    Log.d(TAG, "method: 반복문 전");
                    Log.d(TAG, "method: 칼럼토탈"+colTotal);
                    Log.d(TAG, "method: 로우토탈"+rowTotal);

                    for(int row=rowIndexStart;row<rowTotal;row++) {




                        if(sheet.getCell(15,row).getContents()!=null && sheet.getCell(16,row).getContents()!=null){
                            String finalLat = sheet.getCell(16,row).getContents().substring(0,2)+"."+sheet.getCell(16,row).getContents().substring(2);
                            String finalLong = sheet.getCell(15,row).getContents().substring(0,3)+"."+sheet.getCell(15,row).getContents().substring(3);
                            Log.d(TAG, "이름"+sheet.getCell(1,row).getContents());
                            Log.d(TAG, "경도테스트: "+finalLat);
                            Log.d(TAG, "위도테스트: "+finalLong);

                           dtos.add(new DrugDTO(sheet.getCell(1,row).getContents(),sheet.getCell(0,row).getContents(),sheet.getCell(2,row).getContents(),Double.parseDouble(finalLat),Double.parseDouble(finalLong)));
                            Log.d(TAG, "리스트에 아이템 넣기 "+row);
                        }
                    }

                    }

                }
            }catch(Exception e){
            e.printStackTrace();
             }

        return dtos;
    }

    //현재위치 클릭 했을 때 동작하는 메소드
    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;   //10초 마다 갱신
        float minDistance = 0;  //위치변화가 없더라도(0) 갱신한다
        Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();
        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener );  //위성
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener ); //기지국
            Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Double latitude = lastLocation.getLatitude();   //위도
                Double longtitude = lastLocation.getLongitude();    //경도
                Toast.makeText(this, latitude+":"+longtitude, Toast.LENGTH_SHORT).show();

            // 중심점 변경
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longtitude), true);

            //현재 위치 마커 표시
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("현재위치");
            marker.setTag(0);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longtitude));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            // 줌 레벨 변경
            mapView.setZoomLevel(4, true);

            mapView.addPOIItem(marker);
           marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longtitude));

           //반경표시
            MapCircle circle1 = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(latitude, longtitude), // center
                    5000, // radius
                    Color.argb(128, 255, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);

        }catch (SecurityException e){
            e.getMessage();

        }


    }

    //찾을 주소를 경도,위도로 바꿔주는 메소드
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location reLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address,1);    //비슷한 결과 최대 5개 넘김
            if((addresses == null) || (addresses.size() == 0)){
                return null;
            }
            Address address1Loc = addresses.get(0);
            //Log.d(TAG, "getLocationFromAddress: "+address1Loc.toString());
            reLocation.setLatitude(address1Loc.getLatitude());
            reLocation.setLongitude(address1Loc.getLongitude());
        }catch (Exception e){
            e.getMessage();
        }

        return  reLocation;
    }

    private class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();   //위도
            Double longtitude = location.getLongitude();    //경도


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d(TAG, "onPOIItemSelected: 1111" );
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //이거 일단 태그 제대로 가져온 후에 if문으로 3가지 경우 나누기
        Log.d(TAG, "onPOIItemSelected: 2222" );
        Toast.makeText(this, String.valueOf(mapPOIItem.getTag()), Toast.LENGTH_SHORT).show();
        if(mapPOIItem.getCustomImageResourceId() == R.drawable.corona_btn){
            showcorona(nearcoronas,String.valueOf(mapPOIItem.getTag()));
        }else if(mapPOIItem.getCustomImageResourceId() == R.drawable.medicine_btn){
            showdrug(neardrugs,String.valueOf(mapPOIItem.getTag()));
        }else if(mapPOIItem.getCustomImageResourceId() == R.drawable.hospital_btn){
            showhospital(nearhospitals,String.valueOf(mapPOIItem.getTag()));
        }



    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.d(TAG, "onPOIItemSelected: 3333" );
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Log.d(TAG, "onPOIItemSelected: 4444" );
    }

    //다이어로그 창 뜨기(주변 코로나 검사소)@SuppressLint("ResourceType")
    private void showcorona(ArrayList<CoronaDTO> nearcoronas, String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("상세정보");

        builder.setMessage("의료기관명: "+nearcoronas.get(Integer.parseInt(tag)).getName()+
                "\n주소: "+nearcoronas.get(Integer.parseInt(tag)).getRearaddr()+
                "\n전화번호: "+nearcoronas.get(Integer.parseInt(tag)).getRealtel());



        builder.setIcon(R.drawable.corona_btn);
        builder.setPositiveButton("전화걸기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+nearcoronas.get(Integer.parseInt(tag)).getRealtel()));
                startActivity(intent);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
            }
        });

        builder.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "취소 버튼이 눌렸습니다. : "+which;

            }


        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    //다이어로그 창 뜨기(주변약국)
    private void showdrug(ArrayList<DrugDTO> neardrugs, String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("상세정보");
        builder.setMessage("약국명: "+neardrugs.get(Integer.parseInt(tag)).getName()+"\n"+
        "주소: "+neardrugs.get(Integer.parseInt(tag)).getAddr()+"\n"+
        "전화번호: "+neardrugs.get(Integer.parseInt(tag)).getTel());
        builder.setIcon(R.drawable.medicine_btn);

        builder.setPositiveButton("전화걸기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+neardrugs.get(Integer.parseInt(tag)).getTel()));
                startActivity(intent);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
            }
        });
        builder.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "취소 버튼이 눌렸습니다. : "+which;

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //다이어로그 창 뜨기(주변 병원)
    private void showhospital(ArrayList<hospitalDTO> nearhospitals, String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("상세정보");
        Log.d(TAG, "마커 태그=? "+tag);
        builder.setMessage("병원이름: "+nearhospitals.get(Integer.parseInt(tag)).getName()+"\n"+
                "주        소: "+nearhospitals.get(Integer.parseInt(tag)).getAddr()+"\n"+
                "전화번호: "+nearhospitals.get(Integer.parseInt(tag)).getTel());
        builder.setIcon(R.drawable.hospital_btn);

        builder.setPositiveButton("전화걸기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+nearhospitals.get(Integer.parseInt(tag)).getTel()));
                startActivity(intent);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
            }
        });
        builder.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "취소 버튼이 눌렸습니다. : "+which;

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
