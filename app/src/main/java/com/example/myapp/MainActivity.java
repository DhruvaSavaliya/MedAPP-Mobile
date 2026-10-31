package com.example.myapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Legacy activity kept empty, app now starts from SplashActivity in com.example.medapp.activities
        finish();
    }
}
