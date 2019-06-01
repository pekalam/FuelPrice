package com.projekt.fuelprice.services.fake;

import android.app.Activity;

import com.projekt.fuelprice.services.interfaces.PermissionsService;

public class FakePermissionsService implements PermissionsService {
    @Override
    public void checkApplicationPermissions(Listener listener, Activity activity) {
        listener.onPermissionsGranted();
    }
}
