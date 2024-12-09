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
    private TextView tvUserId;  // TextView to display user ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        ImageView imageViewBarcode = findViewById(R.id.imageViewBarcode);

        // Get userId passed from DashboardActivity
        tvUserId = findViewById(R.id.tvUserId);  // TextView to display user ID

        // Get user ID passed from AuthActivity
        String userId = getIntent().getStringExtra("userId");

        if (userId != null) {
            tvUserId.setText("User ID: " + userId);  // Display user ID
        }

        // Generate barcode from userId
        Bitmap barcodeBitmap = generateBarcode(userId);
        if (barcodeBitmap != null) {
            imageViewBarcode.setImageBitmap(barcodeBitmap);
        } else {
            Toast.makeText(this, "Failed to generate barcode.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap generateBarcode(String text) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
