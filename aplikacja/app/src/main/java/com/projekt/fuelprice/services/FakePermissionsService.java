package com.projekt.fuelprice.services;

import android.app.Activity;

public class FakePermissionsService implements PermissionsService {
    @Override
    public void checkApplicationPermissions(Listener listener, Activity activity) {
        listener.onPermissionsDenied();
    }
}
