package com.projekt.fuelprice.services;

import android.support.v4.util.Consumer;

import com.projekt.fuelprice.data.GasStation;

/**
 * Interfejs implementowany przez klientow api
 */
public interface AsyncMapApiClient {
    /**
     * Pobiera stacje benzynowe w poblizu zadanej lokalizacji
     * @param latitude
     * @param longitude
     * @param radius
     * @param onSuccess
     * @param onError
     */
    void getNearbyGasStations(double latitude, double longitude, int radius,
                              Consumer<GasStation[]> onSuccess, Consumer<Throwable> onError);
}