package com.projekt.fuelprice.viewmodels;

import androidx.annotation.NonNull;

import com.projekt.fuelprice.data.GasStationsRepository;
import com.projekt.fuelprice.services.interfaces.ApplicationSettingsService;
import com.projekt.fuelprice.services.interfaces.DistanceService;
import com.projekt.fuelprice.services.interfaces.LocationService;

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
