package com.projekt.fuelprice;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.arch.lifecycle.ViewModelStore;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    public MapFragment() {
        // Required empty public constructor
    }

    private GoogleMap mMap;

    private GasStationsViewModel gasStationsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
            Zainicjowanie wspolnego viewmodelu
         */
        gasStationsViewModel = ViewModelProviders.of(getActivity(), new GasStationsViewModelFactory(this.getActivity().getApplication())).get(GasStationsViewModel.class);

        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        return fragment;
    }

    /*
        Miejsce na podlaczenie logiki zwiazanej z mapka
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*
            Co ma sie stac jak lista stacji w viewmodel ulegnie zmianie.
            Observe tutaj bo przy apply changes w android studio jest bug
         */
        gasStationsViewModel.getGasStations().observe(this ,new Observer<GasStation[]>() {
            @Override
            public void onChanged(@Nullable GasStation[] gasStations) {

                for (GasStation station: gasStations
                ) {
                    //dodanie markera do mapy
                    mMap.addMarker(new MarkerOptions().position(new LatLng(station.lat, station.lon)).title(station.name).snippet(station.brandName));
                }

            }
        });

        LatLng rze = new LatLng(50.041187, 21.999121);

        //pobranie listy stacji (test)
        if(gasStationsViewModel.getGasStations().getValue() == null)
            gasStationsViewModel.loadGasStations(new LatLng(50.041187, 21.999121), 1000);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rze, 11f));
    }
}
