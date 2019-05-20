package com.projekt.fuelprice.services;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStation;

/**
 * Pobiera logo dla stacji
 * TODO: serwis pobierajacy logo z sieci?
 */
public interface GasStationLogoService {
    interface Listener{
        void onSuccess(Drawable drawable);
    }
    @NonNull
    void getGasStationLogo(GasStation gasStation, GasStationLogoService.Listener listener) throws Exception;
}
