package com.example.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageButton b1;
    Button b2;
    EditText t1;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja widżetów
        b1 = findViewById(R.id.imageButton);
        b2 = findViewById(R.id.button2);
        t1 = findViewById(R.id.t1);
        spinner = findViewById(R.id.languageSpinner);

        // Ustawienie adaptera dla spinnera (listy rozwijanej)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Obsługa wyboru języka w spinnerze
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                switch (position) {
                    case 1: // English
                        setLocale("en");
                        break;
                    case 2: // Spanish
                        setLocale("es");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nie robi nic, gdy nic nie zostanie wybrane
            }
        });

        // Obsługa kliknięcia ImageButton (b1)
        b1.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, getString(R.string.welcome_google), Toast.LENGTH_SHORT).show();
            String url = "https://www.google.pl/?hl=pl";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        // Obsługa kliknięcia Button (b2)
        b2.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, getString(R.string.welcome_newpage), Toast.LENGTH_SHORT).show();
            String url = t1.getText().toString();
            if (!url.isEmpty() && url.startsWith("http")) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } else {

            }
        });
    }

    // Metoda zmieniająca język aplikacji
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Restart aplikacji w celu zastosowania zmiany języka
        recreate();
    }
}
