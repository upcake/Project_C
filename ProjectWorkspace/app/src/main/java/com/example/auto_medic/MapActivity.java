package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity  {
    Button nowPos;
    Boolean check;
    MapView mapView;
    private static final String TAG = "MapActivity";
    //박형규가 만든거
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        nowPos = findViewById(R.id.nowLocation);
        mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        nowPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationService();
            }
        });
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
            mapView.setZoomLevel(2, true);

            mapView.addPOIItem(marker);
           marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longtitude));

            MapCircle circle1 = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(latitude, longtitude), // center
                    500, // radius
                    Color.argb(128, 255, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);

        }catch (SecurityException e){
            e.getMessage();

        }


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

    //권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE

        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    //권한2
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
