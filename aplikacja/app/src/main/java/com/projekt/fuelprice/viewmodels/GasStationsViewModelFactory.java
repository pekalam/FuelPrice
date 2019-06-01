package com.projekt.fuelprice.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.interfaces.ApplicationSettingsService;
import com.projekt.fuelprice.services.interfaces.DistanceService;
import com.projekt.fuelprice.services.interfaces.LocationService;

public class GasStationsViewModelFactory implements ViewModelProvider.Factory {
    private GasStationsVMDepedencies viewModelDepedencies = new GasStationsVMDepedencies();

    public GasStationsViewModelFactory(GasStationsRepository gasStationsRepository, DistanceService distanceService,
                                       LocationService locationService, ApplicationSettingsService applicationSettingsService) {
        viewModelDepedencies.gasStationsRepository = gasStationsRepository;
        viewModelDepedencies.distanceService = distanceService;
        viewModelDepedencies.locationService = locationService;
        viewModelDepedencies.applicationSettingsService = applicationSettingsService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GasStationsViewModel(viewModelDepedencies);
    }
}
