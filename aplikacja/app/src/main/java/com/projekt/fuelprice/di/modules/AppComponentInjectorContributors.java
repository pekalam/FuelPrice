package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.GasStationsFragment;
import com.projekt.fuelprice.HomeActivity;
import com.projekt.fuelprice.MapFragment;
import com.projekt.fuelprice.viewmodels.GasStationsViewModel;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppComponentInjectorContributors {

    @ContributesAndroidInjector
    abstract MapFragment bindMapFragment();

    @ContributesAndroidInjector
    abstract GasStationsFragment bindGasStationsFragment();

    @ContributesAndroidInjector
    abstract HomeActivity bindHomeActivity();
}