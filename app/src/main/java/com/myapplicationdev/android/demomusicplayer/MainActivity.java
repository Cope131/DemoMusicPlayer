package com.myapplicationdev.android.demomusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE = 1;
    private Intent serviceIntent;
//    private MyServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        serviceIntent = new Intent(this, MyService.class);;
//        myServiceConnection = new MyServiceConnection();
    }

    private void initViews() {
        Button startBtn, stopBtn, bindBtn, unbindBtn;
        startBtn = findViewById(R.id.play_button);
        stopBtn = findViewById(R.id.stop_button);
//        bindBtn = findViewById(R.id.bind_sevice_button);
//        unbindBtn = findViewById(R.id.unbind_service_button);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
//        bindBtn.setOnClickListener(this);
//        unbindBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (serviceIntent == null) return;
        switch (v.getId()) {
            case R.id.play_button:
                askPermission();
                break;
            case R.id.stop_button:
                stopService();
                break;
//            case R.id.bind_sevice_button:
//                bindService();
//                break;
//            case R.id.unbind_service_button:
//                unbindService();
        }
    }

    private void askPermission() {
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            return;
        }
        startService();
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (checkPermission()) {
                startService();
                return;
            }
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopService() {
        stopService(serviceIntent);
    }

    private void startService() {
        startService(serviceIntent);
    }

    //    private void unbindService() {
//        unbindService(myServiceConnection);
//    }
//
//    private void bindService() {
//        bindService(serviceIntent, myServiceConnection, BIND_AUTO_CREATE);
//    }
}