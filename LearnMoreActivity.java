package com.example.helper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearnMoreActivity extends AppCompatActivity {

    private TextView textViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);

        textViewInfo = findViewById(R.id.textViewInfo);
        textViewInfo.setText("Blood donation is a voluntary service that saves lives. We encourage healthy individuals to donate blood regularly to support those in need. Thank you for your contribution!");
    }
}
