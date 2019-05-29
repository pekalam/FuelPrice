package com.projekt.fuelprice.services;

import com.projekt.fuelprice.data.GasStation;

public interface ApplicationSettingsService {
    GasStation.FuelType getSelectedFuelType();
    void setSelectedFuelType(GasStation.FuelType type);
}
