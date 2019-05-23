package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentMapBinding;
import com.projekt.fuelprice.utils.DistanceUtils;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    public MapFragment() {
        // Required empty public constructor
    }

    private GoogleMap mMap;

    private GasStationsViewModel gasStationsViewModel;

    private FragmentMapBinding binding;

    @Inject
    GasStationsViewModelFactory gasStationsViewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        /*
            Zainicjowanie wspolnego viewmodelu
         */
        gasStationsViewModel = ViewModelProviders.of(getActivity(), gasStationsViewModelFactory).get(GasStationsViewModel.class);

        /*
            Na bindingu sie nie da
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        return binding.getRoot();
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
        gasStationsViewModel.getGasStations().observe(getViewLifecycleOwner() ,new Observer<GasStation[]>() {
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
            gasStationsViewModel.loadGasStations(new LatLng(50.041187, 21.999121), 2000);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rze, 11f));

        LatLng l = mMap.getProjection().getVisibleRegion().nearLeft;
        LatLng r = mMap.getProjection().getVisibleRegion().farRight;
        double dist = DistanceUtils.distanceBetween(l.latitude, l.longitude, r.latitude, r.longitude);

    }
}
