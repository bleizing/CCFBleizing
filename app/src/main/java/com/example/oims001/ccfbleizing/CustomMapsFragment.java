package com.example.oims001.ccfbleizing;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomMapsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "CustomMapsFragment";

    private GoogleMap mMap;

    private List<Address> addressList;
    private ArrayList<String> addressArrayList;
    private ArrayAdapter<String> adapter;

    private Geocoder geocoder;

    private AutoCompleteTextView actv_input_address;

    public CustomMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        return view;

        // Inflate the layout for this fragment
        //        return inflater.inflate(R.layout.fragment_custom_maps, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).setActionBarTitle("Custom Maps");

        addressArrayList = new ArrayList<>();


        actv_input_address = (AutoCompleteTextView) getActivity().findViewById(R.id.actv_input_address);

        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,addressArrayList);
        actv_input_address.setAdapter(adapter);
        actv_input_address.setThreshold(3);

        actv_input_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 2) {
                    getAddress(s.toString());
                    adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, addressArrayList);
                    actv_input_address.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actv_input_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address selectedAddress = addressList.get(position);

                mMap.clear();

                setMarker(selectedAddress.getLatitude(), selectedAddress.getLongitude());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getMaxZoomLevel();
        mMap.getMinZoomLevel();
        mMap.getUiSettings();
        setMarker(-6.175126, 106.827142);
    }

    private void setMarker(double lat, double lng) {
        LatLng latlng = new LatLng(lat, lng);

        mMap.addMarker(new MarkerOptions().position(latlng)).showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));
    }

    private void getAddress(String inputAddress) {
        if (geocoder == null) {
            geocoder = new Geocoder(getActivity());
        }

        if (addressArrayList.size() != 0) {
            addressArrayList.clear();
        }

        try {
            addressList = geocoder.getFromLocationName(inputAddress, 10);
            for (int i = 0; i < addressList.size(); i++) {
                String address = addressList.get(i).getAddressLine(0);
                Log.w(TAG, "address = " + address);

                addressArrayList.add(address);
            }

            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
