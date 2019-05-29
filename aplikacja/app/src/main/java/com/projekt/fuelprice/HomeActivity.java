package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.projekt.fuelprice.databinding.ActivityHomeBinding;
import com.projekt.fuelprice.services.ApplicationSettingsService;


import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;





public class HomeActivity extends FragmentActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ApplicationSettingsService settingsService;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        //dzieki temu pod binding.fab jest ten przycisk ktory w xml ma id fab
        //tak samo jest ze wszystkim czemu nadacie id w xml
        //dla kazdej innnej aktywnosci i fragmentu mozna robic tak samo
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FuelSelectionBottomSheetDialogFragment fuelSelectionBottomSheetDialogFragment =
                        FuelSelectionBottomSheetDialogFragment.newInstance(settingsService);

                fuelSelectionBottomSheetDialogFragment.show(getSupportFragmentManager(),
                        "fuel_bottom_sheet");
            }
        });

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}



