package com.example.helper;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DonationHistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<Donation> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);

        listViewHistory = findViewById(R.id.listViewHistory);
        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, historyList);
        listViewHistory.setAdapter(historyAdapter);

        loadHistory();
    }

    private void loadHistory() {
        // Get UID pengguna yang login
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Pengguna tidak terautentikasi.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = auth.getCurrentUser().getUid();

        // Reference to user's donation history in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Donors").child(currentUserId).child("history");

        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                historyList.clear();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Donation donation = snapshot.getValue(Donation.class);
                    if (donation != null) {
                        historyList.add(donation);
                    }
                }
                historyAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Tidak ada data riwayat.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
