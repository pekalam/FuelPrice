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
    private Handler nextActivityHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powitanie);
        getSupportActionBar().hide();
        permissionCheckUtility = new PermissionCheckUtility(permissionsService, this);
        nextActivityHandler = new Handler();
    }

    private Runnable startHomeActivity = new Runnable(){
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onPause() {
        nextActivityHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onResume() {
        permissionCheckUtility.check(this);
        super.onResume();
    }

    @Override
    public void onPermissionsGranted() {
        nextActivityHandler.postDelayed(startHomeActivity, 1000);
    }

    @Override
    public void onPermissionsDenied() {
        permissionCheckUtility.tryAgain();
    }
}
