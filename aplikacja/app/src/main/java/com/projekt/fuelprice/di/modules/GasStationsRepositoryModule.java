package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.data.WebGasStationsRepository;
import com.projekt.fuelprice.services.AsyncMapApiClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = AppModule.class
)
public class GasStationsRepositoryModule {
    @Provides
    @Named("gasStationsRepository")
    GasStationsRepository provideGasStationsRepository(@Named("asyncMapApiClient")AsyncMapApiClient asyncMapApiClient){
        return new WebGasStationsRepository(asyncMapApiClient);
    }
}
