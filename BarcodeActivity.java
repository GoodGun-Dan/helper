package com.example.helper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeActivity extends AppCompatActivity {
    private TextView tvUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        ImageView imageViewBarcode = findViewById(R.id.imageViewBarcode);
        tvUserId = findViewById(R.id.tvUserId);

        // Get userId passed from previous activity
        String userId = getIntent().getStringExtra("userId");

        if (userId != null && !userId.isEmpty()) {
            tvUserId.setText("User ID: " + userId);

            // Generate barcode
            Bitmap barcodeBitmap = generateBarcode(userId);
            if (barcodeBitmap != null) {
                imageViewBarcode.setImageBitmap(barcodeBitmap);
            } else {
                Toast.makeText(this, "Failed to generate barcode.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User ID is missing.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap generateBarcode(String text) {
        if (text == null || text.trim().isEmpty()) {
            Toast.makeText(this, "Cannot generate barcode for empty text.", Toast.LENGTH_SHORT).show();
            return null;
        }

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating barcode.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
