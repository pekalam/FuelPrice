package com.projekt.fuelprice.viewmodels;

import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.ApplicationSettingsService;
import com.projekt.fuelprice.services.DistanceService;
import com.projekt.fuelprice.services.LocationService;

public class GasStationsVMDepedencies {
    @NonNull
    public GasStationsRepository gasStationsRepository;
    @NonNull
    public DistanceService distanceService;
    @NonNull
    public LocationService locationService;
    @NonNull
    public ApplicationSettingsService applicationSettingsService;
}
