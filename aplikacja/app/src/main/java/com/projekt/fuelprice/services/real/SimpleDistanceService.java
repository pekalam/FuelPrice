package com.projekt.fuelprice.services.real;

import android.util.Log;

import com.projekt.fuelprice.services.interfaces.DistanceService;

public class SimpleDistanceService implements DistanceService {
    @Override
    public void findDistance(double lat1, double lon1, double lat2, double lon2, Listener listener) {
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        double distance = Radius * c;
        Log.d("A:SimpleDistanceService", "Zmierzona odlegosc: " + distance);
        listener.onDistanceFound(distance);
    }
}
