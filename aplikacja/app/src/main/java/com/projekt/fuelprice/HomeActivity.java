package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.projekt.fuelprice.databinding.ActivityHomeBinding;


import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class HomeActivity extends FragmentActivity implements HasSupportFragmentInjector {
    private InterstitialAd mInterstitialAd;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private ActivityHomeBinding binding;

    FuelSelectionBottomSheetDialogFragment fuelSelectionBottomSheetDialogFragment;
    VoiceRecognitionFragment voiceRecognitionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        fuelSelectionBottomSheetDialogFragment =
                FuelSelectionBottomSheetDialogFragment.newInstance();
        binding.bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuelSelectionBottomSheetDialogFragment.show(getSupportFragmentManager(),
                        "fuel_bottom_sheet");
            }
        });
        binding.fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                voiceRecognitionFragment = VoiceRecognitionFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.mainLayout, voiceRecognitionFragment);
                transaction.commit();
                return true;
            }
        });
        MobileAds.initialize(this, "ca-app-pub-5768880253719503~7329247068");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(voiceRecognitionFragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(voiceRecognitionFragment);
            transaction.commit();
            voiceRecognitionFragment = null;
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}



