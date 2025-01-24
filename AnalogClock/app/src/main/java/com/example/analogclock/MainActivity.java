package com.example.analogclock;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnalogClockView analogClock = findViewById(R.id.analogClock);
        analogClock.setTime(13, 27, 30);
    }
}
