package com.projekt.fuelprice.data;

import android.support.v4.util.Consumer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Operacje dotyczace wyszukiwania stacji
 */
public interface GasStationsRepository {
    void getGasStations(LatLng pos, int radius, Consumer<GasStation[]> onSuccess);
}
