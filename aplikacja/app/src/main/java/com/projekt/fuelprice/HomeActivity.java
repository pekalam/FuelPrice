package com.projekt.fuelprice;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.projekt.fuelprice.databinding.ActivityHomeBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;

/*
TODO: pasek nawigacji
 */
public class HomeActivity extends FragmentActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

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

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
