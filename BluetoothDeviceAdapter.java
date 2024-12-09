package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BluetoothDeviceAdapter extends ArrayAdapter<String> {

    public BluetoothDeviceAdapter(Context context, ArrayList<String> devices) {
        super(context, R.layout.list_item_bluetooth_device, devices); // Gunakan layout kustom
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_bluetooth_device, parent, false);
        }

        String device = getItem(position);
        String[] deviceDetails = device.split(" - "); // Pisahkan nama dan alamat

        TextView tvDeviceName = convertView.findViewById(R.id.tvDeviceName);
        TextView tvDeviceAddress = convertView.findViewById(R.id.tvDeviceAddress);

        tvDeviceName.setText(deviceDetails[0]); // Nama perangkat
        tvDeviceAddress.setText(deviceDetails[1]); // Alamat perangkat
        return convertView;
    }
}

