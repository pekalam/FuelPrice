package com.projekt.fuelprice.data;

import androidx.core.util.Consumer;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.projekt.fuelprice.services.AsyncMapApiClient;
import com.projekt.fuelprice.services.GasStationLogoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Domyslne repozytorium stacji korzystajace z AsyncMapApiClient
 */
public class WebGasStationsRepository implements GasStationsRepository {
    private AsyncMapApiClient apiClient;
    private GasStationLogoService logoService;

    public WebGasStationsRepository(AsyncMapApiClient apiClient, GasStationLogoService logoService){
        this.apiClient = apiClient;
        this.logoService = logoService;
    }

    @Override
    public void getGasStations(LatLng pos, int radius, final Consumer<GasStation[]> onSuccess) {
        
        apiClient.getNearbyGasStations(pos.latitude, pos.longitude, radius, new Consumer<GasStation[]>() {
            /*
            TODO: callback hell
             */
            @Override
            public void accept(final GasStation[] gasStations) {

                final ArrayList<Integer> li = new ArrayList<Integer>();
                final GasStationLogoService.Listener l = new GasStationLogoService.Listener() {
                    @Override
                    public void onSuccess(Drawable drawable) {
                        li.add(1);
                        if(li.size() == gasStations.length)
                            onSuccess.accept(gasStations);
                    }
                };


                for (final GasStation station: gasStations
                     ) {
                    try {
                        logoService.getGasStationLogo(station, new GasStationLogoService.Listener() {
                            @Override
                            public void onSuccess(Drawable drawable) {
                                station.logo = drawable;
                                l.onSuccess(drawable);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("ERROR", "WebGasStationsRepository: logo service error", e);
                        return;
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.d("ERROR", "WebGasStationsRepository: error", throwable);
            }
        });
    }
}
