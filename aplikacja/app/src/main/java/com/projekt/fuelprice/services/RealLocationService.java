package com.projekt.fuelprice.services;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class RealLocationService implements LocationService {

    private Context context;

    public RealLocationService(Context context){
        this.context = context;
    }

    @Override
    public void startService(final Listener listener) {
        SmartLocation.with(this.context)
                .location()
                .continuous()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        listener.onLocationChanged(latLng);
                    }
                });
    }

    @Override
    public void singleRequest(final Listener listener) {
        SmartLocation.with(this.context)
                .location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        listener.onLocationChanged(latLng);
                    }
                });
    }
}
