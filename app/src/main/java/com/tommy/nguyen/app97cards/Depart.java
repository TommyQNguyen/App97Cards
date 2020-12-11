package com.tommy.nguyen.app97cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Depart extends AppCompatActivity {

    Button boutonDepart;
    Ecouteur ecouteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depart);

        // 1ere etape:
        ecouteur = new Ecouteur();

        Button boutonDepart = findViewById(R.id.boutonDepart);

        boutonDepart.setOnClickListener(ecouteur);
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            Intent intention = new Intent ( Depart.this, MainActivity.class );
            startActivity(intention);
        }
    }
}
