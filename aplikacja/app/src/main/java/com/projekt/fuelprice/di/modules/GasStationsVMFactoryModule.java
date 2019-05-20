package com.projekt.fuelprice.di.modules;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.viewmodels.GasStationsViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = GasStationsRepositoryModule.class
)
public class GasStationsVMFactoryModule {
    @Provides
    GasStationsViewModelFactory provideGasStationsViewModelFactory(@Named("gasStationsRepository") GasStationsRepository gasStationsRepository){
        return new GasStationsViewModelFactory(gasStationsRepository);
    }
}
