package com.example.helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.getStartedButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.learnMoreButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LearnMoreActivity.class);
            startActivity(intent);
        });
    }
}
