package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentMapBinding;
import com.projekt.fuelprice.services.GasStationLogoService;
import com.projekt.fuelprice.services.LocationService;
import com.projekt.fuelprice.services.PermissionsService;
import com.projekt.fuelprice.utils.DistanceUtils;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    public MapFragment() {
        // Required empty public constructor
    }

    private GoogleMap mMap;

    private GasStationsViewModel gasStationsViewModel;

    private FragmentMapBinding binding;

    @Inject
    GasStationsViewModelFactory gasStationsViewModelFactory;

    @Inject
    PermissionsService permissionsService;

    @Inject
    LocationService locationService;


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
        mMap.setTrafficEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                locationService.singleRequest(new LocationService.Listener() {
                    @Override
                    public void onLocationChanged(LatLng newLocation) {
                        
                    }
                });
                return false;
            }
        });
        permissionsService.checkServicesAvailability(new PermissionsService.Listener() {
            @Override
            public void onPermissionsGranted() {
                mMap.setMyLocationEnabled(true);
            }

            @Override
            public void onPermissionsDenied() {

            }

            @Override
            public void onRequiredServicesEnabled() {

            }

            @Override
            public void onRequiredServicesDisabled() {

            }
        }, getActivity());
        /*
            Co ma sie stac jak lista stacji w viewmodel ulegnie zmianie.
            Observe tutaj bo przy apply changes w android studio jest bug
         */
        gasStationsViewModel.getGasStations().observe(getViewLifecycleOwner() ,new Observer<GasStation[]>() {
            @Override
            public void onChanged(@Nullable GasStation[] gasStations) {

                for (final GasStation station: gasStations
                ) {

                    BitmapDrawable bitmapdraw=(BitmapDrawable)station.logo;
                    Bitmap b=bitmapdraw.getBitmap();
                    //TODO
                    //ZMIANA ROZMIARU LOGO NA POTRZEBY WYSWIETLENIA JAKO MARKER
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 48,48, false);

                    //dodanie markera do mapy
                    MarkerOptions options = new MarkerOptions()
                            .position(new LatLng(station.lat, station.lon))
                            .title(station.name)
                            .snippet(station.brandName)
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    mMap.addMarker(options);

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
