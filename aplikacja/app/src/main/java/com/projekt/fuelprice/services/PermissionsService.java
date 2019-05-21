package com.projekt.fuelprice.services;

import android.app.Activity;

/**
 * Sprawdza czy aplikacja posiada wszystkie uprawnienia
 * i czy wlaczone sa wymagane uslugi.
 * TODO
 */
public interface PermissionsService {
    interface Listener{
        void onPermissionsGranted();
        void onPermissionsDenied();
        void onRequiredServicesEnabled();
        void onRequiredServicesDisabled();
    }

    /**
     * Sprawdza czy aplikacja posiada uprawnienia do usluch
     * i czy sa wlaczone
     * @param listener
     * @param activity
     */
    void checkServicesAvailability(Listener listener, Activity activity);
}
