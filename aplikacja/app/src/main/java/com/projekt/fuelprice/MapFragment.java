package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static float MIN_ZOOM = 9.7f;
    private static float LOCATION_FOUND_ZOOM = 11.6f;
    private static int ZOOM_CHECK_INTERVAL = 500;
    private static float ZOOM_CHECK_THRESHOLD = 0.15f;
    private static int ZOOM_CHECK_RADIUS_THRESHOLD = 2000;

    public MapFragment() {
        // Required empty public constructor
    }

    private GoogleMap mMap;
    private Handler zoomCheckHandler;
    private float currentZoom;
    private Map<GasStation, Marker> gasStationToMarker = new HashMap<>();
    private Map<Marker, GasStation> markerToGasStation = new HashMap<>();
    private Marker selectedMarker;

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
        gasStationsViewModel = ViewModelProviders.of(getActivity(), gasStationsViewModelFactory).get(GasStationsViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gasStationsViewModel.getCurrentPosition().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng newLocation) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, LOCATION_FOUND_ZOOM));
                currentZoom = mMap.getCameraPosition().zoom;
                int searchingRadius = calcSearchingRadius();
                gasStationsViewModel.setSearchingRadius(searchingRadius);
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
                gasStationToMarker.get(gasStation).showInfoWindow();
            }
        });

        gasStationsViewModel.getSelectedFuelType().observe(getViewLifecycleOwner(), new Observer<GasStation.FuelType>() {
            @Override
            public void onChanged(GasStation.FuelType fuelType) {
                if(selectedMarker != null){
                    selectedMarker.hideInfoWindow();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        if(mMap != null) {
            gasStationsViewModel.startFindingCurrentPosition();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        gasStationsViewModel.stopFindingCurrentPosition();
        super.onPause();
    }

    private Runnable zoomCheck = new Runnable() {
        @Override
        public void run() {
            float newZoom = mMap.getCameraPosition().zoom;
            //jeżeli użytkownik oddala lub przybliza
            if(currentZoom - newZoom > ZOOM_CHECK_THRESHOLD ||
                    newZoom - currentZoom > ZOOM_CHECK_THRESHOLD){
                //nowy promien w obrebie ktorego szukane sa stacje
                int newRadius = calcSearchingRadius();
                int currentRadius = gasStationsViewModel.getSearchingRadius().getValue();
                //jezeli uzytkownik chce wyszukac w obrebie wiekszego lub mniejszego promienia
                if(newRadius - currentRadius > ZOOM_CHECK_RADIUS_THRESHOLD || currentRadius - newRadius > ZOOM_CHECK_RADIUS_THRESHOLD){
                    gasStationsViewModel.setSearchingRadius(newRadius);
                    //TODO cache
                    gasStationsViewModel.forceLoadGasStations();
                }
                currentZoom = newZoom;
                //jesli mapa jest maksymalnie oddalona a maksymalny promien wyszukiwania nie zostal osiagniety

            }
            if(currentZoom == MIN_ZOOM && gasStationsViewModel.getSearchingRadius().getValue() < GasStationsViewModel.MAX_SEARCHING_RADIUS){
                MIN_ZOOM -= 0.5f;
                mMap.setMinZoomPreference(MIN_ZOOM);
            }

            zoomCheckHandler.removeCallbacks(zoomCheck);
            zoomCheckHandler.postDelayed(zoomCheck, ZOOM_CHECK_INTERVAL);
        }
    };

    private int calcSearchingRadius(){
        LatLng l = mMap.getProjection().getVisibleRegion().nearLeft;
        LatLng r = mMap.getProjection().getVisibleRegion().farRight;
        double dist = DistanceUtils.distanceBetween(l.latitude, l.longitude, r.latitude, r.longitude);
        return ((int)dist*1000)/2;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(MIN_ZOOM);
        //mMap.setMaxZoomPreference(19f);
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
                for (Marker marker: markerToGasStation.keySet()
                     ) {
                    marker.remove();
                }
                selectedMarker = null;
                gasStationToMarker.clear();
                markerToGasStation.clear();
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
                    Marker marker = mMap.addMarker(options);
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            if(markerToGasStation.containsKey(marker)){
                                selectedMarker = marker;
                                GasStation gasStation = markerToGasStation.get(marker);
                                gasStationsViewModel.setSelectedGasStation(gasStation);
                            }
                            else{
                                throw new NullPointerException();
                            }
                            return false;
                        }
                    });
                    gasStationToMarker.put(station, marker);
                    markerToGasStation.put(marker, station);
                }

            }
        });

        zoomCheckHandler = new Handler();
        zoomCheckHandler.postDelayed(zoomCheck, ZOOM_CHECK_INTERVAL);
    }
}
