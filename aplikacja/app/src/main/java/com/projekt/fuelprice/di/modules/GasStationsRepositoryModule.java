package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.data.WebGasStationsRepository;
import com.projekt.fuelprice.services.AsyncMapApiClient;
import com.projekt.fuelprice.services.GasStationLogoService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = AppModule.class
)
public class GasStationsRepositoryModule {
    @Provides
    @Named("gasStationsRepository")
    GasStationsRepository provideGasStationsRepository(@Named("asyncMapApiClient")AsyncMapApiClient asyncMapApiClient,
                                                       @Named("gasStationLogoService")GasStationLogoService logoService){
        return new WebGasStationsRepository(asyncMapApiClient, logoService);
    }
}
