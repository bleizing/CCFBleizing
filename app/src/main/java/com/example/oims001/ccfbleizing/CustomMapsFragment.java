package com.example.oims001.ccfbleizing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsInsideFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public MapsInsideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_maps_inside, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        return view;

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_maps_inside, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng monas = new LatLng(-6.175126, 106.827142);
        mMap.addMarker(new MarkerOptions().position(monas).title("Monas")).showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(monas, 17));
    }
}
