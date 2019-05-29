package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.DistanceService;
import com.projekt.fuelprice.services.LocationService;
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
                                                                   @Named("locationService")LocationService locationService){
        return new GasStationsViewModelFactory(gasStationsRepository, distanceService, locationService);
    }
}
