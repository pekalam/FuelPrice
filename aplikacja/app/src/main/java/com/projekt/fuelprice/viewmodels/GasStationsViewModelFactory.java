package com.projekt.fuelprice.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.services.DistanceService;
import com.projekt.fuelprice.services.LocationService;

public class GasStationsViewModelFactory implements ViewModelProvider.Factory {

    private GasStationsRepository gasStationsRepository;

    private DistanceService distanceService;

    private LocationService locationService;

    private ApplicationSettingsService applicationSettingsService;

    public GasStationsViewModelFactory(GasStationsRepository gasStationsRepository, DistanceService distanceService, LocationService locationService, ApplicationSettingsService applicationSettingsService) {
        this.gasStationsRepository = gasStationsRepository;
        this.distanceService = distanceService;
        this.locationService = locationService;
        this.applicationSettingsService = applicationSettingsService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GasStationsViewModel(gasStationsRepository, distanceService, locationService, applicationSettingsService);
    }
}
