package com.projekt.fuelprice.data;

import android.support.v4.util.Consumer;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.projekt.fuelprice.services.AsyncMapApiClient;

/**
 * Domyslne repozytorium stacji korzystajace z AsyncMapApiClient
 */
public class WebGasStationsRepository implements GasStationsRepository {
    private AsyncMapApiClient apiClient;

    public WebGasStationsRepository(AsyncMapApiClient apiClient){
        this.apiClient = apiClient;
    }

    @Override
    public void getGasStations(LatLng pos, int radius, final Consumer<GasStation[]> onSuccess) {
        apiClient.getNearbyGasStations(pos.latitude, pos.longitude, radius, new Consumer<GasStation[]>() {
            @Override
            public void accept(GasStation[] gasStations) {
                onSuccess.accept(gasStations);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.d("ERROR", "WebGasStationsRepository: error", throwable);
            }
        });
    }
}
