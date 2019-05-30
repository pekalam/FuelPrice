package com.projekt.fuelprice;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.projekt.fuelprice.services.PermissionsService;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class Powitanie extends AppCompatActivity implements PermissionsService.Listener {

    @Inject
    PermissionsService permissionsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powitanie);
        permissionsService.checkServicesAvailability(this, this);
        getSupportActionBar().hide();

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

    }

    @Override
    public void onRequiredServicesEnabled() {

    }

    @Override
    public void onRequiredServicesDisabled() {

    }
}
