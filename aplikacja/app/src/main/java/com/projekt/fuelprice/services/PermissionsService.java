package com.projekt.fuelprice.services;

import android.app.Activity;

/**
 * Sprawdza czy aplikacja posiada wszystkie uprawnienia
 * i czy wlaczone sa wymagane uslugi.
 * Serwis  uzywany przez widoki (fragmenty) aby uzyskac dostep do
 * innych serwisow / widgetow wymagajacych uprawnien
 */
public interface PermissionsService {
    interface Listener{
        void onPermissionsGranted();
        void onPermissionsDenied();
    }

    /**
     * Sprawdza czy aplikacja posiada uprawnienia do usluch
     * i czy sa wlaczone
     * @param listener
     * @param activity
     */
    void checkApplicationPermissions(Listener listener, Activity activity);
}
