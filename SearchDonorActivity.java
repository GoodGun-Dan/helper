package com.example.helper;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchDonorActivity extends AppCompatActivity {

    private Spinner spinnerBloodGroupFilter;
    private ListView listViewDonors;
    private DonorAdapter donorAdapter;
    private ArrayList<Donor> donorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);

        spinnerBloodGroupFilter = findViewById(R.id.spinnerBloodGroupFilter);
        listViewDonors = findViewById(R.id.listViewDonors);

        donorList = new ArrayList<>();
        donorAdapter = new DonorAdapter(this, donorList);
        listViewDonors.setAdapter(donorAdapter);

        loadDonors();
    }

    private void loadDonors() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Donors");
        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                donorList.clear();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Donor donor = snapshot.getValue(Donor.class);
                    donorList.add(donor);
                }
                donorAdapter.notifyDataSetChanged();
            }
        });
    }
}
