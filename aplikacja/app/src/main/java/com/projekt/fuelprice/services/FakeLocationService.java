package com.projekt.fuelprice.services;

import com.google.android.gms.maps.model.LatLng;

public class FakeLocationService implements LocationService {
    @Override
    public void startService(Listener listener) {
        listener.onLocationChanged(new LatLng(50.041187, 21.999121));
    }

    @Override
    public void singleRequest(Listener listener) {
        listener.onLocationChanged(new LatLng(50.041187, 21.999121));
    }

    @Override
    public void stopService() {

    }
}
