package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SetPinActivity extends AppCompatActivity {

    TextView tvAppName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        tvAppName = findViewById(R.id.tvAppName);

        tvAppName.setTypeface(null, Typeface.BOLD);
    }
}