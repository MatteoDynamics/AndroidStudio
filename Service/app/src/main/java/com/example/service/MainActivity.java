package com.example.service; // Zastąp com.example.servicetestapp nazwą swojego pakietu

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private Button buttonDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonDownload = findViewById(R.id.buttonDownload);

        buttonDownload.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString();

            // Utwórz Intent do uruchomienia DownloadService
            Intent intent = new Intent(this, DownloadService.class);
            intent.putExtra("url", url); // Dodaj adres URL do Intent

            // Uruchom serwis
            startService(intent);
        });
    }
}