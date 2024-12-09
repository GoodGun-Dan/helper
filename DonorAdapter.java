package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Donor> donors;

    public DonorAdapter(Context context, ArrayList<Donor> donors) {
        this.context = context;
        this.donors = donors;
    }

    @Override
    public int getCount() {
        return donors.size();
    }

    @Override
    public Object getItem(int position) {
        return donors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.donor_list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.donorName);
        TextView bloodGroup = convertView.findViewById(R.id.donorBloodGroup);
        TextView location = convertView.findViewById(R.id.donorLocation);

        Donor donor = donors.get(position);
        name.setText(donor.getName());
        bloodGroup.setText(donor.getBloodGroup());
        location.setText(donor.getLocation());

        return convertView;
    }
}
