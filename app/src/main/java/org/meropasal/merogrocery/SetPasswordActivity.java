package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Typeface;

public class SetPasswordActivity extends AppCompatActivity {
    TextView tvAppName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        tvAppName = findViewById(R.id.tvAppName);

        tvAppName.setTypeface(null, Typeface.BOLD);
    }
}