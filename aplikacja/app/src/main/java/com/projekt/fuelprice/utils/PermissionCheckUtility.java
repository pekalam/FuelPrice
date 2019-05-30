package com.projekt.fuelprice.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.projekt.fuelprice.services.PermissionsService;

public class PermissionCheckUtility {
    private AlertDialog dialog;
    private PermissionsService.Listener listener;
    private PermissionsService permissionsService;
    private Activity callingActivity;

    public PermissionCheckUtility(@NonNull final PermissionsService permissionsService,@NonNull final Activity callingActivity){
        this.permissionsService = permissionsService;
        this.callingActivity = callingActivity;
        dialog = new AlertDialog.Builder(callingActivity)
                .setTitle("Brak wystarczających uprawnień")
                .setMessage("Aplikacja FuelPrice nie może działać bez dostępu do lokalizacji.")
                .setPositiveButton("Ponów próbę", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                permissionsService.checkApplicationPermissions(listener, callingActivity);
                            }
                        }, 200);

                    }
                })
                .setNegativeButton("Wyjdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callingActivity.finish();
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            callingActivity.finish();
                            dialog.dismiss();
                        }
                        return true;
                    }
                })
                .create();
    }

    public void check(@NonNull PermissionsService.Listener listener){
        this.listener = listener;
        permissionsService.checkApplicationPermissions(new PermissionsService.Listener() {
            @Override
            public void onPermissionsGranted() {
                PermissionCheckUtility.this.listener.onPermissionsGranted();
            }

            @Override
            public void onPermissionsDenied() {
                dialog.show();
            }
        }, callingActivity);
    }

    public void tryAgain(){
        if(this.listener == null) {
            throw new NullPointerException();
        }
        dialog.show();
    }
}
