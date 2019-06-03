package com.projekt.fuelprice.services.interfaces;

import android.graphics.Bitmap;
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
    interface ListenerMiniLogo{
        void onSuccess(Bitmap bitmap);
    };
    void getGasStationLogo(GasStation gasStation, GasStationLogoService.Listener listener) throws Exception;
    void getGasStationMiniLogo(GasStation gasStation, GasStationLogoService.ListenerMiniLogo listener) throws Exception;
}
