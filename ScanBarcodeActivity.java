package com.example.helper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class ScanBarcodeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Donors");

        // Mulai pemindaian barcode
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan barcode pendonor");
        integrator.setCameraId(0); // Kamera belakang
        integrator.setBeepEnabled(true); // Suara saat berhasil scan
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // Hasil pemindaian (UID pendonor)
                String donorUid = result.getContents();
                Toast.makeText(this, "Barcode ID: " + donorUid, Toast.LENGTH_SHORT).show();

                // Dapatkan UID dan Nama penerima dari aplikasi
                String recipientUid = "recipientUID";  // UID penerima (dapatkan dari login Firebase)
                String recipientName = "Nama Penerima"; // Nama penerima (dapatkan dari form atau Firebase)

                // Perbarui riwayat pendonor di Firebase
                updateDonorHistory(donorUid, recipientUid, recipientName);

                // Tampilkan pesan setelah scan berhasil
                Toast.makeText(this, "Selamat Anda telah mendapatkan donor dari UID: " + donorUid, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Pemindaian dibatalkan.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateDonorHistory(String donorUid, String recipientUid, String recipientName) {
        // Perbarui riwayat pendonor di Firebase
        HashMap<String, Object> updateData = new HashMap<>();
        updateData.put("recipientUid", recipientUid);
        updateData.put("recipientName", recipientName);

        databaseReference.child(donorUid).child("history").push().setValue(updateData)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Riwayat pendonor berhasil diperbarui!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Gagal memperbarui riwayat pendonor.", Toast.LENGTH_SHORT).show());
    }
}
