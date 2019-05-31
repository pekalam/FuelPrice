package com.projekt.fuelprice.utils;

import android.util.Log;

import java.text.DecimalFormat;

public class DistanceUtils {
    public static double distanceBetween(double lat1, double lon1, double lat2, double lon2){
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        double distance = Radius * c;
        double retDistance = Math.round(distance);
        Log.d("A:DistanceUtils", "Zmierzona odlegosc [km]: " + retDistance);
        return retDistance;
    }
}
