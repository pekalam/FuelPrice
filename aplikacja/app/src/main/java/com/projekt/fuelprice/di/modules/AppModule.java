package com.projekt.fuelprice.di.modules;

import android.app.Application;
import com.projekt.fuelprice.services.AsyncMapApiClient;
import com.projekt.fuelprice.services.FakePermissionsService;
import com.projekt.fuelprice.services.FakeTomTomApiClient;
import com.projekt.fuelprice.services.GasStationLocalLogoService;
import com.projekt.fuelprice.services.GasStationLogoService;
import com.projekt.fuelprice.services.PermissionsService;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {


    @Provides
    PermissionsService providePermissionsService(){
        return new FakePermissionsService();
    }

    @Singleton
    @Provides
    @Named("asyncMapApiClient")
    AsyncMapApiClient provideAsyncMapApiClient(Application app){
        return new FakeTomTomApiClient(app.getApplicationContext());
    }

    @Singleton
    @Provides
    @Named("gasStationLogoService")
    GasStationLogoService provideGasStationLogoService(Application app){
        return new GasStationLocalLogoService(app.getApplicationContext());
    }

}
