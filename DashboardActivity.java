package com.example.helper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnRegisterDonor, btnSearchDonor, btnViewHistory, btnShowBarcode, btnBluetooth, btnScanBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        btnRegisterDonor = findViewById(R.id.btnRegisterDonor);
        btnSearchDonor = findViewById(R.id.btnSearchDonor);
        btnViewHistory = findViewById(R.id.btnHistory);
        btnShowBarcode = findViewById(R.id.btnShowBarcode);  // Button for showing barcode
        btnBluetooth = findViewById(R.id.btnBluetooth);      // Button for Bluetooth
        btnScanBarcode = findViewById(R.id.btnScanBarcode);  // Button for scanning barcode

        // Get user ID passed from AuthActivity
        String userId = getIntent().getStringExtra("userId");

        // Navigate to Register Donor Form
        btnRegisterDonor.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, DonorFormActivity.class);
            startActivity(intent);
        });

        // Navigate to Search Donors
        btnSearchDonor.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SearchDonorActivity.class);
            startActivity(intent);
        });

        // Navigate to Donation History
        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, DonationHistoryActivity.class);
            startActivity(intent);
        });

        // Navigate to Barcode Activity
        btnShowBarcode.setOnClickListener(v -> {
            // Send userId to BarcodeActivity
            Intent intent = new Intent(DashboardActivity.this, BarcodeActivity.class);
            intent.putExtra("userId", userId);  // Pass userId to BarcodeActivity
            startActivity(intent);
        });

        // Navigate to Bluetooth Activity
        btnBluetooth.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, BluetoothActivity.class);
            startActivity(intent);
        });

        // Navigate to Scan Barcode Activity
        btnScanBarcode.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ScanBarcodeActivity.class);
            startActivity(intent);
        });
    }
}
