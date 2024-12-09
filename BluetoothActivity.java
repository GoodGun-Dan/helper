package com.example.helper;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 2;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<String> pairedDevicesList;
    private BluetoothDeviceAdapter deviceAdapter;

    private Button btnEnableBluetooth, btnDisableBluetooth, btnMakeVisible, btnDiscoverDevices;
    private ListView listViewPairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        // Initialize Bluetooth adapter safely
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        }

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth tidak didukung pada perangkat ini.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize UI components
        btnEnableBluetooth = findViewById(R.id.btnEnableBluetooth);
        btnDisableBluetooth = findViewById(R.id.btnDisableBluetooth);
        btnMakeVisible = findViewById(R.id.btnMakeVisible);
        btnDiscoverDevices = findViewById(R.id.btnDiscoverDevices);
        listViewPairedDevices = findViewById(R.id.listViewPairedDevices);

        pairedDevicesList = new ArrayList<>();
        deviceAdapter = new BluetoothDeviceAdapter(this, pairedDevicesList);
        listViewPairedDevices.setAdapter(deviceAdapter);

        // Set up button actions
        btnEnableBluetooth.setOnClickListener(v -> enableBluetooth());
        btnDisableBluetooth.setOnClickListener(v -> disableBluetooth());
        btnMakeVisible.setOnClickListener(v -> makeDeviceVisible());
        btnDiscoverDevices.setOnClickListener(v -> discoverDevices());
    }

    private void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            Toast.makeText(this, "Bluetooth sudah diaktifkan.", Toast.LENGTH_SHORT).show();
        }
    }

    private void disableBluetooth() {
        if (bluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            bluetoothAdapter.disable();
            Toast.makeText(this, "Bluetooth dinonaktifkan.", Toast.LENGTH_SHORT).show();

            // Reset UI data
            pairedDevicesList.clear();
            deviceAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Bluetooth sudah dinonaktifkan.", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeDeviceVisible() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            requestBluetoothPermissions();
            return;
        }

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    private void discoverDevices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            requestBluetoothPermissions();
            return;
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        pairedDevicesList.clear();

        if (pairedDevices != null && !pairedDevices.isEmpty()) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName() != null ? device.getName() : "Unknown Device";
                String deviceAddress = device.getAddress();
                pairedDevicesList.add(deviceName + " - " + deviceAddress);
            }
            deviceAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Tidak ada perangkat yang terhubung.", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestBluetoothPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_ADVERTISE
                },
                REQUEST_BLUETOOTH_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin Bluetooth diberikan.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Izin Bluetooth diperlukan untuk melanjutkan.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            Toast.makeText(this, "Bluetooth berhasil diaktifkan.", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_ENABLE_BT) {
            Toast.makeText(this, "Bluetooth tidak diaktifkan.", Toast.LENGTH_SHORT).show();
        }
    }
}
