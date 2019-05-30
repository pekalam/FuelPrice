package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projekt.fuelprice.data.GasStation;
import com.projekt.fuelprice.databinding.FragmentMapBinding;
import com.projekt.fuelprice.services.PermissionsService;
import com.projekt.fuelprice.utils.DistanceUtils;
import com.projekt.fuelprice.utils.PermissionCheckUtility;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static float MIN_ZOOM = 9.7f;

    public MapFragment() {
        // Required empty public constructor
    }

    private GoogleMap mMap;

    private GasStationsViewModel gasStationsViewModel;

    private FragmentMapBinding binding;

    private Marker mPositionMarker;

    private Toast _testToast;

    private PermissionCheckUtility permissionCheckUtility;

    @Inject
    GasStationsViewModelFactory gasStationsViewModelFactory;

    @Inject
    PermissionsService permissionsService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        permissionCheckUtility = new PermissionCheckUtility(permissionsService, getActivity());
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

        gasStationsViewModel.getCurrentPosition().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng newLocation) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 11.6f));
                MarkerOptions positionMarker = new MarkerOptions()
                        .position(newLocation);
                if(mPositionMarker != null) {
                    mPositionMarker.remove();
                }
                mPositionMarker = mMap.addMarker(positionMarker);

                if(_testToast != null)
                    _testToast.cancel();
                _testToast = Toast.makeText(getContext(), "[TEST] Znaleziono bieżącą lokalizacje", Toast.LENGTH_LONG);
                _testToast.show();
            }
        });

        gasStationsViewModel.getSelectedGasStation().observe(getViewLifecycleOwner(), new Observer<GasStation>() {
            @Override
            public void onChanged(GasStation gasStation) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gasStation.lat, gasStation.lon)));
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(MIN_ZOOM);
        //mMap.setMaxZoomPreference(19f);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                return false;
            }
        });

        LatLng l = mMap.getProjection().getVisibleRegion().nearLeft;
        LatLng r = mMap.getProjection().getVisibleRegion().farRight;
        double dist = DistanceUtils.distanceBetween(l.latitude, l.longitude, r.latitude, r.longitude);

        permissionCheckUtility.check(new PermissionsService.Listener() {
            @Override
            public void onPermissionsGranted() {
                gasStationsViewModel.startFindingCurrentPosition();
                if(_testToast != null)
                    _testToast.cancel();
                _testToast = Toast.makeText(getContext(), "[TEST] Trwa wyszukiwanie bieżącej lokalizacji...", Toast.LENGTH_LONG);
                _testToast.show();
            }

            @Override
            public void onPermissionsDenied() {
                permissionCheckUtility.tryAgain();
            }

        });

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




        //pobranie listy stacji (test)
        //if(gasStationsViewModel.getGasStations().getValue() == null)
          //  gasStationsViewModel.loadGasStations(new LatLng(50.041187, 21.999121), 2000);

    }
}
