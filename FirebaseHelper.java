package com.example.helper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Donors");
    }

    public void addDonor(Donor donor, final DataCallback callback) {
        String donorId = databaseReference.push().getKey();
        if (donorId != null) {
            databaseReference.child(donorId).setValue(donor).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            });
        }
    }

    public void getAllDonors(final DataCallback callback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Donor> donorList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Donor donor = snapshot.getValue(Donor.class);
                    donorList.add(donor);
                }
                callback.onSuccess(donorList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure();
            }
        });
    }

    public interface DataCallback {
        void onSuccess();
        void onFailure();
        void onSuccess(ArrayList<Donor> donors);
    }
}
