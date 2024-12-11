package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Donation> {

    private Context context;
    private ArrayList<Donation> donations;

    public HistoryAdapter(Context context, ArrayList<Donation> donations) {
        super(context, R.layout.item_donation, donations);
        this.context = context;
        this.donations = donations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_donation, parent, false);
        }

        Donation donation = donations.get(position);

        TextView tvRecipientName = convertView.findViewById(R.id.tvRecipientName);
        TextView tvRecipientUid = convertView.findViewById(R.id.tvRecipientUid);
        TextView tvDonationDate = convertView.findViewById(R.id.tvDonationDate);

        tvRecipientName.setText("Penerima: " + donation.getRecipientName());
        tvRecipientUid.setText("UID: " + donation.getRecipientUid());
        tvDonationDate.setText("Tanggal: " + donation.getDonationDate());

        return convertView;
    }
}
