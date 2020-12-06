package com.tommy.nguyen.app97cards;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Conclusion extends AppCompatActivity {
    TextView pointageFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conclusion);

        pointageFinal = findViewById(R.id.pointageFinal);
        String points = getIntent().getStringExtra("points");
        pointageFinal.setText(points);
    }
}
