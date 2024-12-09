package com.example.helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class DonorFormActivity extends AppCompatActivity {

    private EditText edtName, edtAge, edtLocation, edtContact;
    private Spinner spinnerBloodGroup;
    private Button btnSubmit;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);

        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtLocation = findViewById(R.id.edtLocation);
        edtContact = findViewById(R.id.edtContact);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Donors");

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String age = edtAge.getText().toString();
            String location = edtLocation.getText().toString();
            String contact = edtContact.getText().toString();
            String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();

            if (name.isEmpty() || age.isEmpty() || location.isEmpty() || contact.isEmpty()) {
                Toast.makeText(DonorFormActivity.this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                // Generate unique user ID (UUID)
                String userId = UUID.randomUUID().toString();

                // Save user ID to SharedPreferences
                SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userId", userId); // Save userId
                editor.apply();

                // Store donor data
                HashMap<String, String> donorData = new HashMap<>();
                donorData.put("name", name);
                donorData.put("age", age);
                donorData.put("location", location);
                donorData.put("contact", contact);
                donorData.put("bloodGroup", bloodGroup);
                donorData.put("userId", userId);  // Save the generated user ID

                // Save data to Firebase
                databaseReference.push().setValue(donorData)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(DonorFormActivity.this, "Data pendonor berhasil disimpan!", Toast.LENGTH_SHORT).show();

                            // Open BarcodeActivity after successful submission
                            Intent intent = new Intent(DonorFormActivity.this, BarcodeActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(DonorFormActivity.this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
