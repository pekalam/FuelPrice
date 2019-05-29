package com.projekt.fuelprice.di.modules;

import android.app.Application;

import com.projekt.fuelprice.services.ApplicationPermissionsService;

import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.services.AsyncMapApiClient;
import com.projekt.fuelprice.services.DistanceService;
import com.projekt.fuelprice.services.FakeApplicationSettingsService;
import com.projekt.fuelprice.services.FakePermissionsService;
import com.projekt.fuelprice.services.FakeTomTomApiClient;
import com.projekt.fuelprice.services.GasStationLocalLogoService;
import com.projekt.fuelprice.services.GasStationLogoService;
import com.projekt.fuelprice.services.LocationService;
import com.projekt.fuelprice.services.PermissionsService;
import com.projekt.fuelprice.services.RealApplicationSettingsService;
import com.projekt.fuelprice.services.RealLocationService;
import com.projekt.fuelprice.services.SimpleDistanceService;
import com.projekt.fuelprice.services.TomTomApiClient;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    @Named("gasStationLogoService")
    GasStationLogoService provideGasStationLogoService(Application app){
        return new GasStationLocalLogoService(app.getApplicationContext());
    }
    @Singleton
    @Provides
    ApplicationSettingsService provideApplicationSettingsService(Application app){
        return new FakeApplicationSettingsService();
    }

    @Singleton
    @Provides
    @Named("locationService")
    LocationService provideLocationService(Application app){
        return new RealLocationService(app.getApplicationContext());
    }

    @Singleton
    @Provides
    @Named("distanceService")
    DistanceService provideDistanceService(){
        return new SimpleDistanceService();
    }

    @Singleton
    @Provides
    PermissionsService providePermissionsService(){
        return new ApplicationPermissionsService();
    }

    @Singleton
    @Provides
    @Named("asyncMapApiClient")
    AsyncMapApiClient provideAsyncMapApiClient(Application app){
        //return new TomTomApiClient();
        return new FakeTomTomApiClient(app.getApplicationContext());
    }

}
