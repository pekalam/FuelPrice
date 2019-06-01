package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.interfaces.ApplicationSettingsService;
import com.projekt.fuelprice.services.interfaces.DistanceService;
import com.projekt.fuelprice.services.interfaces.LocationService;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = GasStationsRepositoryModule.class
)
public class GasStationsVMFactoryModule {
    @Provides
    GasStationsViewModelFactory provideGasStationsViewModelFactory(@Named("gasStationsRepository") GasStationsRepository gasStationsRepository,
                                                                   @Named("distanceService")DistanceService distanceService,
                                                                   @Named("locationService")LocationService locationService,
                                                                   @Named("applicationSettingsService")ApplicationSettingsService applicationSettingsService){
        return new GasStationsViewModelFactory(gasStationsRepository, distanceService, locationService, applicationSettingsService);
    }
}
