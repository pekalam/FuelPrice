package com.projekt.fuelprice.services.interfaces;

import com.google.android.gms.maps.model.LatLng;

public interface LocationService {
    interface Listener{
        void onLocationChanged(LatLng newLocation);
    }
    void startService(Listener listener);
    void singleRequest(Listener listener);
    void stopService();
}
