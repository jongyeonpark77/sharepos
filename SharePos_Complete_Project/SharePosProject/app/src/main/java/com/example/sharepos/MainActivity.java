package com.example.sharepos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.5665, 126.9780); // 서울 시청

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private MaterialButton btnCurrentLocation;
    private MaterialButton btnShareLocation;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위치 서비스 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // UI 요소 초기화
        btnCurrentLocation = findViewById(R.id.btn_current_location);
        btnShareLocation = findViewById(R.id.btn_share_location);

        // 지도 프래그먼트 설정
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // 버튼 클릭 리스너 설정
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        btnCurrentLocation.setOnClickListener(v -> {
            if (checkLocationPermission()) {
                getCurrentLocation();
            } else {
                requestLocationPermission();
            }
        });

        btnShareLocation.setOnClickListener(v -> {
            if (currentLocation != null) {
                shareLocation();
            } else {
                Toast.makeText(this, "먼저 현재 위치를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        
        // 지도 기본 설정
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        
        // 기본 위치로 카메라 이동 (서울 시청)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15));
        
        // 위치 권한 확인
        if (checkLocationPermission()) {
            enableMyLocation();
            getCurrentLocation();
        } else {
            requestLocationPermission();
        }
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            
                            // 지도에 현재 위치 마커 추가
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions()
                                    .position(currentLocation)
                                    .title("현재 위치"));
                            
                            // 카메라를 현재 위치로 이동
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                            
                            Toast.makeText(this, "현재 위치를 표시했습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "위치를 가져올 수 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void shareLocation() {
        if (currentLocation != null) {
            String locationText = String.format("내 현재 위치: %.6f, %.6f", 
                    currentLocation.latitude, currentLocation.longitude);
            
            // Google Maps 링크 생성
            String mapsUrl = String.format("https://maps.google.com/?q=%.6f,%.6f", 
                    currentLocation.latitude, currentLocation.longitude);
            
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, locationText + "\n\n" + mapsUrl);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SharePos - 위치 공유");
            
            startActivity(Intent.createChooser(shareIntent, "위치 공유하기"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
                getCurrentLocation();
            } else {
                Toast.makeText(this, "위치 권한이 필요합니다", Toast.LENGTH_LONG).show();
            }
        }
    }
}
