package com.example.oims001.ccfbleizing;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {
    private static final String TAG = "MapsFragment";

    private EditText edit_lat, edit_lng;
    private TextView tv_address;

    private double longitude = 0;
    private double latitude = 0;

    private LocationManager locationManager;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).setActionBarTitle("Maps");

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getCurrentLocation();

        edit_lat = (EditText) getActivity().findViewById(R.id.edit_lat);
        edit_lng = (EditText) getActivity().findViewById(R.id.edit_lng);

        tv_address = (TextView) getActivity().findViewById(R.id.tv_address);

        Button btn_show_address = (Button) getActivity().findViewById(R.id.btn_show_address);
        btn_show_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_lat.getText().toString().equals("") && !edit_lng.getText().toString().equals("")) {
                    latitude = Double.parseDouble(edit_lat.getText().toString());
                    longitude = Double.parseDouble(edit_lng.getText().toString());
                }
                getAddress();
            }
        });

        Button btn_get_current_location = (Button) getActivity().findViewById(R.id.btn_get_current_location);
        btn_get_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(locationListener);
            }
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            Log.d(TAG, "lat : " + latitude + " & lng : " + longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    };

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);

            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }
    }

    private void getAddress() {
        if (longitude != 0 && latitude != 0) {
            StringBuilder result = new StringBuilder();
            try {
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    result.append(address.getLocality()).append("\n");
                    result.append(address.getCountryName()).append("\n");
                    result.append(address.getAddressLine(0)).append("\n");
                    result.append(address.getPostalCode()).append("\n");
                    result.append(address.getSubAdminArea()).append("\n");
                    result.append(address.getAdminArea()).append("\n");
                    result.append(address.getLatitude()).append("\n");
                    result.append(address.getLongitude()).append("\n");
                    result.append(address.getPhone()).append("\n");
                    result.append(address.getPremises()).append("\n");
                    result.append(address.getSubLocality()).append("\n");
                    result.append(address.getThoroughfare()).append("\n");
                    result.append(address.getSubThoroughfare()).append("\n");
                    result.append(address.getUrl()).append("\n");
                    result.append(address.getMaxAddressLineIndex()).append("\n");

                    Log.w(TAG, result.toString());
                    tv_address.setText(address.getAddressLine(0));
                }
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
        } else {
            tv_address.setText("Titik Lokasi Tidak Dapat Ditemukan");
        }
    }
}
