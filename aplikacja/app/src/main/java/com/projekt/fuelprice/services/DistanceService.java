package com.projekt.fuelprice.services;

public interface DistanceService {
    interface Listener{
        void onDistanceFound(double distance);
        void onDistanceServiceError();
    }

    void findDistance(double sLat, double sLon, double eLat, double eLon, Listener listener);
}
