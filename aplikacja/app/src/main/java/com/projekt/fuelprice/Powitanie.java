package com.projekt.fuelprice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.projekt.fuelprice.services.PermissionsService;
import com.projekt.fuelprice.utils.PermissionCheckUtility;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class Powitanie extends AppCompatActivity implements PermissionsService.Listener {

    @Inject
    PermissionsService permissionsService;

    private PermissionCheckUtility permissionCheckUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powitanie);
        getSupportActionBar().hide();
        permissionCheckUtility = new PermissionCheckUtility(permissionsService, this);
        permissionCheckUtility.check(this);
    }

    @Override
    public void onPermissionsGranted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    @Override
    public void onPermissionsDenied() {
        permissionCheckUtility.tryAgain();
    }
}
