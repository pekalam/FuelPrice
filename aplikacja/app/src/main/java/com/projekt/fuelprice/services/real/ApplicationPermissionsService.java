package com.projekt.fuelprice.services.real;

import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.projekt.fuelprice.services.interfaces.PermissionsService;

import java.util.List;

public class ApplicationPermissionsService implements PermissionsService {
    @Override
    public void checkApplicationPermissions(final Listener listener, Activity activity) {
        Dexter.withActivity(activity).withPermissions("android.permission.ACCESS_FINE_LOCATION", "android.permission.RECORD_AUDIO")
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted())
                            listener.onPermissionsGranted();
                        else
                            listener.onPermissionsDenied();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}
