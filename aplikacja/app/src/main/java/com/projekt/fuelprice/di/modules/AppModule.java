package com.projekt.fuelprice.di.modules;

import android.app.Application;

import com.projekt.fuelprice.services.real.ApplicationPermissionsService;

import com.projekt.fuelprice.services.interfaces.ApplicationSettingsService;
import com.projekt.fuelprice.services.interfaces.AsyncMapApiClient;
import com.projekt.fuelprice.services.interfaces.DistanceService;
import com.projekt.fuelprice.services.fake.FakeApplicationSettingsService;
import com.projekt.fuelprice.services.fake.FakeLocationService;
import com.projekt.fuelprice.services.fake.FakeTomTomApiClient;
import com.projekt.fuelprice.services.fake.FakeVoiceRecognitionService;
import com.projekt.fuelprice.services.real.GasStationLocalLogoService;
import com.projekt.fuelprice.services.interfaces.GasStationLogoService;
import com.projekt.fuelprice.services.interfaces.LocationService;
import com.projekt.fuelprice.services.interfaces.PermissionsService;
import com.projekt.fuelprice.services.real.SimpleDistanceService;
import com.projekt.fuelprice.services.interfaces.VoiceRecognitionService;

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
    @Named("applicationSettingsService")
    ApplicationSettingsService provideApplicationSettingsService(Application app){
        return new FakeApplicationSettingsService();
    }

    @Singleton
    @Provides
    @Named("locationService")
    LocationService provideLocationService(Application app){
        return new FakeLocationService();
        //return new RealLocationService(app.getApplicationContext());
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
        //return new FakePermissionsService();
        return new ApplicationPermissionsService();
    }

    @Singleton
    @Provides
    @Named("asyncMapApiClient")
    AsyncMapApiClient provideAsyncMapApiClient(Application app){
        //return new TomTomApiClient();
        return new FakeTomTomApiClient(app.getApplicationContext());
    }

    @Singleton
    @Provides
    @Named("voiceRecognitionService")
    VoiceRecognitionService provideVoiceRecognitionService(Application app){
        return new FakeVoiceRecognitionService();
        //return new RealVoiceRecognitionService(app.getApplicationContext());
    }

}
